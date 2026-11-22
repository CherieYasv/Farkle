package farklepractica.modelo;

public abstract class Jugador {

    private String nombre;
    private int puntos;
    private boolean enJuego;   // ya anotó sus primeros 500 o más

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.enJuego = false;
    }

    public void sumarPuntos(int cantidad) {
        puntos = puntos + cantidad;
        enJuego = true;
    }

    public abstract boolean esHumano();

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean estaEnJuego() {
        return enJuego;
    }
}
