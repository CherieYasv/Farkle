package farklepractica.modelo;

import java.util.ArrayList;

/**
 * Clase que tiene el control del juego. Aquí están TODAS las reglas;
 * la interfaz gráfica solo muestra lo que hay aquí y manda las acciones del jugador.
 */
public class JuegoFarkle {

    private static final int CANTIDAD_DADOS = 6;
    private static final int PUNTOS_PARA_ENTRAR = 500;
    private static final int PUNTOS_PARA_GANAR = 10000;

    private ArrayList<Jugador> jugadores;
    private ArrayList<Dado> dados;
    private ArrayList<String> combosTurno;

    private int turnoActual;
    private int puntosTurno;
    private boolean tiradaPendiente;   // ya se tiraron los dados y falta apartar
    private boolean ultimaRonda;
    private int jugadorQueLlegoAMeta;
    private boolean juegoTerminado;
    private String mensaje;

    public JuegoFarkle(int humanos, int computadoras) {
        jugadores = new ArrayList<>();
        dados = new ArrayList<>();
        combosTurno = new ArrayList<>();

        for (int i = 1; i <= humanos; i = i + 1) {
            jugadores.add(new JugadorHumano("Jugador " + i));
        }

        for (int i = 1; i <= computadoras; i = i + 1) {
            jugadores.add(new JugadorComputadora("Compu " + i));
        }

        // Si solo hay un humano (o nadie), se agrega una computadora automáticamente
        int totalComputadoras = computadoras;
        while (jugadores.size() < 2) {
            totalComputadoras = totalComputadoras + 1;
            jugadores.add(new JugadorComputadora("Compu " + totalComputadoras));
        }

        for (int i = 0; i < CANTIDAD_DADOS; i = i + 1) {
            dados.add(new Dado());
        }

        turnoActual = 0;
        puntosTurno = 0;
        tiradaPendiente = false;
        ultimaRonda = false;
        jugadorQueLlegoAMeta = -1;
        juegoTerminado = false;
        mensaje = "Turno de " + jugadorActual().getNombre() + ".";
    }

    // ---------------------------------------------------------------
    // Acciones del jugador
    // ---------------------------------------------------------------

    // Tira los dados que no están apartados
    public void tirar() {
        for (Dado dado : dados) {
            if (dado.isApartado() == false) {
                dado.tirar();
            }
        }
        tiradaPendiente = true;

        if (Puntuacion.hayPuntos(valoresDisponibles()) == false) {
            mensaje = "¡FARKLE! " + jugadorActual().getNombre()
                    + " pierde los " + puntosTurno + " puntos del turno.";
            pasarTurno();
        }
    }

    // Marca o desmarca un dado para apartarlo
    public void seleccionarDado(int indice) {
        Dado dado = dados.get(indice);

        if (tiradaPendiente == false) {
            return;
        }
        if (dado.isApartado() == true) {
            return;
        }

        if (dado.isSeleccionado() == true) {
            dado.setSeleccionado(false);
        } else {
            dado.setSeleccionado(true);
        }
    }

    // Aparta los dados seleccionados y suma sus puntos al turno
    public boolean apartarSeleccion() {
        ArrayList<Integer> valores = valoresSeleccionados();

        if (valores.size() == 0) {
            mensaje = "Selecciona al menos un dado que valga puntos.";
            return false;
        }

        int puntos = Puntuacion.calcular(valores);
        if (puntos == 0) {
            mensaje = "Hay dados seleccionados que no valen puntos.";
            return false;
        }

        puntosTurno = puntosTurno + puntos;
        combosTurno.add(Puntuacion.describir(valores) + " (" + puntos + ")");
        mensaje = "Combos: " + String.join(", ", combosTurno);

        for (Dado dado : dados) {
            if (dado.isSeleccionado() == true) {
                dado.setApartado(true);
                dado.setSeleccionado(false);
            }
        }
        tiradaPendiente = false;

        if (todosApartados() == true) {
            limpiarDados();
            mensaje = mensaje + " ¡Los 6 dados valieron! Puedes tirar los 6 otra vez.";
        }

        return true;
    }

    // Guarda los puntos del turno en el marcador y pasa el turno
    public boolean plantarse() {
        Jugador jugador = jugadorActual();

        if (puntosTurno == 0) {
            mensaje = "Primero tira los dados.";
            return false;
        }

        if (jugador.estaEnJuego() == false && puntosTurno < PUNTOS_PARA_ENTRAR) {
            mensaje = "Necesitas al menos " + PUNTOS_PARA_ENTRAR
                    + " puntos en un turno para anotar por primera vez.";
            return false;
        }

        jugador.sumarPuntos(puntosTurno);
        mensaje = jugador.getNombre() + " anota " + puntosTurno + " puntos.";

        if (jugador.getPuntos() >= PUNTOS_PARA_GANAR && ultimaRonda == false) {
            ultimaRonda = true;
            jugadorQueLlegoAMeta = turnoActual;
            mensaje = mensaje + " ¡Llegó a " + PUNTOS_PARA_GANAR
                    + "! Los demás tienen un último turno.";
        }

        pasarTurno();
        return true;
    }

    // ---------------------------------------------------------------
    // Jugador no humano: cada llamada hace un solo paso
    // ---------------------------------------------------------------

    public void jugarPasoComputadora() {
        if (juegoTerminado == true) {
            return;
        }

        if (tiradaPendiente == false) {
            tirar();
            return;
        }

        seleccionarDadosQueValenPuntos();
        apartarSeleccion();

        if (debePlantarse() == true) {
            plantarse();
        }
    }

    private void seleccionarDadosQueValenPuntos() {
        ArrayList<Integer> valores = valoresDisponibles();
        int[] conteo = Puntuacion.contar(valores);
        boolean todosValen = Puntuacion.calcular(valores) > 0;

        for (Dado dado : dados) {
            if (dado.isApartado() == false) {
                int valor = dado.getValor();

                if (todosValen == true) {
                    dado.setSeleccionado(true);
                } else if (valor == 1 || valor == 5 || conteo[valor] >= 3) {
                    dado.setSeleccionado(true);
                }
            }
        }
    }

    private boolean debePlantarse() {
        Jugador jugador = jugadorActual();
        int total = jugador.getPuntos() + puntosTurno;

        // Todavía no puede anotar, tiene que seguir tirando
        if (jugador.estaEnJuego() == false && puntosTurno < PUNTOS_PARA_ENTRAR) {
            return false;
        }

        // Última ronda: sigue hasta superar al que va ganando
        if (ultimaRonda == true) {
            if (total > jugadorConMasPuntos().getPuntos()) {
                return true;
            }
            return false;
        }

        if (total >= PUNTOS_PARA_GANAR) {
            return true;
        }
        if (puntosTurno >= 500) {
            return true;
        }
        if (valoresDisponibles().size() <= 2 && puntosTurno >= 300) {
            return true;
        }

        return false;
    }

    // ---------------------------------------------------------------
    // Métodos de apoyo
    // ---------------------------------------------------------------

    private void pasarTurno() {
        puntosTurno = 0;
        tiradaPendiente = false;
        combosTurno.clear();
        limpiarDados();

        turnoActual = (turnoActual + 1) % jugadores.size();

        if (ultimaRonda == true && turnoActual == jugadorQueLlegoAMeta) {
            juegoTerminado = true;
            Jugador ganador = jugadorConMasPuntos();
            mensaje = mensaje + " FIN DEL JUEGO. Gana " + ganador.getNombre()
                    + " con " + ganador.getPuntos() + " puntos.";
            return;
        }

        mensaje = mensaje + " Turno de " + jugadorActual().getNombre() + ".";
    }

    // LAMBDA: quita lo apartado y lo seleccionado de todos los dados
    private void limpiarDados() {
        dados.forEach(dado -> {
            dado.setApartado(false);
            dado.setSeleccionado(false);
        });
    }

    // LAMBDA: ordena una copia de la lista de mayor a menor puntaje
    private Jugador jugadorConMasPuntos() {
        ArrayList<Jugador> ordenados = new ArrayList<>(jugadores);
        ordenados.sort((jugadorA, jugadorB) -> {
            return jugadorB.getPuntos() - jugadorA.getPuntos();
        });
        return ordenados.get(0);
    }

    private ArrayList<Integer> valoresDisponibles() {
        ArrayList<Integer> valores = new ArrayList<>();
        for (Dado dado : dados) {
            if (dado.isApartado() == false) {
                valores.add(dado.getValor());
            }
        }
        return valores;
    }

    private ArrayList<Integer> valoresSeleccionados() {
        ArrayList<Integer> valores = new ArrayList<>();
        for (Dado dado : dados) {
            if (dado.isSeleccionado() == true) {
                valores.add(dado.getValor());
            }
        }
        return valores;
    }

    private boolean todosApartados() {
        for (Dado dado : dados) {
            if (dado.isApartado() == false) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------
    // Getters para que la vista y el controlador lean el estado
    // ---------------------------------------------------------------

    public Jugador jugadorActual() {
        return jugadores.get(turnoActual);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<Dado> getDados() {
        return dados;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getPuntosTurno() {
        return puntosTurno;
    }

    public boolean isTiradaPendiente() {
        return tiradaPendiente;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public String getMensaje() {
        return mensaje;
    }
}
