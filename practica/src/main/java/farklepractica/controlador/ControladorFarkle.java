package farklepractica.controlador;

import farklepractica.vista.VistaFarkle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Conecta la vista con el modelo: recibe los clics, se los pasa al juego
 * y le pide a la vista que se actualice.
 */
public class ControladorFarkle {

    private JuegoFarkle juego;
    private VistaFarkle vista;
    private int cantidadBots;
    private boolean botsElegidos;

    public ControladorFarkle(VistaFarkle vista) {
        this.vista = vista;
        this.juego = null;
        this.botsElegidos = false;

        vista.mostrarPregunta("¿Cuántos bots van a jugar?");
        conectarBotonesSeleccion();
    }

    // ---------------------------------------------------------------
    // Selección de bots y personas
    // ---------------------------------------------------------------

    // LAMBDAS: cada botón de la selección manda su número al controlador
    private void conectarBotonesSeleccion() {
        for (int i = 0; i < vista.getBotonesSeleccion().size(); i = i + 1) {
            int cantidad = i;
            vista.getBotonesSeleccion().get(i).setOnAction(evento -> {
                alElegirCantidad(cantidad);
            });
        }
    }

    // La primera vez es la cantidad de bots, la segunda la de personas
    private void alElegirCantidad(int cantidad) {
        if (botsElegidos == false) {
            cantidadBots = cantidad;
            botsElegidos = true;
            vista.mostrarPregunta("¿Cuántas personas van a jugar?");
            return;
        }

        empezarJuego(cantidad);
    }

    private void empezarJuego(int personas) {
        juego = new JuegoFarkle(personas, cantidadBots);
        vista.mostrarJuego(juego);
        conectarEventosJuego();
        vista.actualizar();
        revisarTurnoComputadora();
    }

    // ---------------------------------------------------------------
    // Juego
    // ---------------------------------------------------------------

    // LAMBDAS: cada botón ejecuta su método cuando se le da clic
    private void conectarEventosJuego() {
        vista.getBotonTirar().setOnAction(evento -> {
            alPresionarTirar();
        });

        vista.getBotonPlantarse().setOnAction(evento -> {
            alPresionarPlantarse();
        });

        for (int i = 0; i < vista.getBotonesDados().size(); i = i + 1) {
            int indice = i;
            vista.getBotonesDados().get(i).setOnAction(evento -> {
                alPresionarDado(indice);
            });
        }
    }

    private void alPresionarDado(int indice) {
        if (juego.jugadorActual().esHumano() == false) {
            return;
        }

        juego.seleccionarDado(indice);
        vista.actualizar();
    }

    private void alPresionarTirar() {
        if (juego.jugadorActual().esHumano() == false) {
            return;
        }

        if (juego.isTiradaPendiente() == true) {
            boolean seApartaron = juego.apartarSeleccion();
            if (seApartaron == false) {
                vista.actualizar();
                return;
            }
        }

        juego.tirar();
        vista.actualizar();
        revisarTurnoComputadora();
    }

    private void alPresionarPlantarse() {
        if (juego.jugadorActual().esHumano() == false) {
            return;
        }

        if (juego.isTiradaPendiente() == true) {
            boolean seApartaron = juego.apartarSeleccion();
            if (seApartaron == false) {
                vista.actualizar();
                return;
            }
        }

        juego.plantarse();
        vista.actualizar();
        revisarTurnoComputadora();
    }

    // Si le toca a una computadora, juega un paso cada 1.2 segundos
    // LAMBDA: lo que se hace cuando termina la pausa
    private void revisarTurnoComputadora() {
        if (juego.isJuegoTerminado() == true) {
            return;
        }
        if (juego.jugadorActual().esHumano() == true) {
            return;
        }

        PauseTransition pausa = new PauseTransition(Duration.seconds(1.2));
        pausa.setOnFinished(evento -> {
            juego.jugarPasoComputadora();
            vista.actualizar();
            revisarTurnoComputadora();
        });
        pausa.play();
    }
}