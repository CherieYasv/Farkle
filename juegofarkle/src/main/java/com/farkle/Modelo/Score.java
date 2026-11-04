package com.farkle.Modelo;

public class Score {
    private int lugar;
    private int puntos;
    private int monedas;
    private String combinacion;
    private String descripcion;
    public Score(){

    }
    public Score(int lugar, int puntos, int monedas, String combinacion, String descripcion){
        this.lugar=lugar;
        this.puntos=puntos;
        this.monedas=monedas;
        this.combinacion=combinacion;
        this.descripcion=descripcion;
    }
    public int getLugar() {
        return lugar;}
    public int getPuntos() {
        return puntos;
    }
    public String getCombinacion(){
        return combinacion;
    }
    public int comparaPuntos(Score score){
        return puntos-score.getPuntos();
    }

    public String toString(){
        return "Combinacion: "+combinacion+" Puntos: "+puntos+" Monedas:"+monedas+" Descripcion: "+descripcion;
    }

}

