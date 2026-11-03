package com.farkle.Modelo;

import java.util.Random;

public class Dado {
    private int numeroCaras;
    private int valorVisible;

    public Dado() {
        numeroCaras = 6;
        lanzar(); // Asigna un valor aleatorio inicial entre 1 y 6
    }

    public Dado(int numeroCaras) {
        this.numeroCaras = numeroCaras;
        lanzar();
    }

    public void setNumeroCaras(int numeroCaras) {
        this.numeroCaras = numeroCaras;
    }

    public int getNumeroCaras() {
        return numeroCaras;
    }

    public void lanzar() {
        Random rmd = new Random();
        valorVisible = rmd.nextInt(numeroCaras) + 1;
    }

    public int getValorVisible() {
        return valorVisible;
    }

    public int getValor() {
        return valorVisible;
    }

    public String toString() {
        return "Dado de " + numeroCaras + " caras | Valor: " + valorVisible;
    }
}