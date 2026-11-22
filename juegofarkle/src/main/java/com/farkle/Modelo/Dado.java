package com.farkle.Modelo;

import java.util.Random;

public class Dado {
    private int valor;
    private boolean seleccionado;
    private boolean apartado;

    public Dado() {
        seleccionado = false;
        apartado = false;
        tirar();
    }

    public void tirar() {
        valor = (int) (Math.random() * 6) + 1;
    }

    public int getValor() {
        return valor;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isApartado() {
        return apartado;
    }

    public void setApartado(boolean apartado) {
        this.apartado = apartado;
    }

    public String toString() {
        return "Dado de " + valor + "seleccionado: " + seleccionado + "apartado: " + apartado;
    }
}