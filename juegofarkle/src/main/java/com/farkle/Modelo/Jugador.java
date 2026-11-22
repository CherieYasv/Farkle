package com.farkle.Modelo;

import java.util.ArrayList;

public abstract class Jugador {
    private String nombre;
    private int puntos;
    private boolean enJuego;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.enJuego = false;
    }

    public Jugador(){
        nombre = "Victor";
        puntos = 0;
        enJuego = false;
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

    public String toString (){
        return "El jugador " + nombre + " tiene la cantidad de puntos: " +  puntos + ", estado de juego: " + enJuego;
    }
}

