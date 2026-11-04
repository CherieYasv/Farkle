package com.farkle.Modelo;

import java.util.ArrayList;

public class Jugador {
    private int fichas;
    private String nombre;
    private int puntos;
    private ArrayList<Dado> dados;

    public Jugador(){
        fichas = 20;
        nombre = "Zanti";
        puntos = 0;
        dados = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            dados.add(new Dado());
        }
    }

    public Jugador(String nombre){
        fichas = 20;
        this.nombre = nombre;
        puntos = 0;
        dados = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            dados.add(new Dado());
        }
    }

    public void lanzar3Dados(){
        for (int i = 0; i < 3; i++){
            dados.get(i).lanzar();
        }
    }

    public int getFichas(){
        return fichas;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntos(){
        return puntos;
    }

    public ArrayList<com.farkle.Modelo.Dado> getDados() {
        return dados;
    }

    public String toString(){
        return "Nombre: " + nombre + "Fichas" + fichas + "Combinacion: " + dados;
    }

    public void incrementaPuntos(int sumar ){
        puntos += sumar;
    }

    public void incrementaMonedas (int sumar){
        fichas += sumar;
    }

}
