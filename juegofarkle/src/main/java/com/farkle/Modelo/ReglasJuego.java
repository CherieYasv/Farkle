package com.farkle.Modelo;

import java.util.ArrayList;

public class ReglasJuego {

    private int ronda;
    private int indiceDeGanador;
    private ArrayList<Jugador> listaJugadores;


    public  ReglasJuego() {
        listaJugadores = new ArrayList<Jugador>();
        ronda = 0;
        indiceDeGanador = 0;
    }


    public void jugarRonda(boolean sn, Jugador jugador) {
        ronda++;

        lanzar(sn, jugador);


    }

    public void lanzar(boolean sn, Jugador jugador) {
        if (sn) {
            jugador.lanzar3Dados();

        }
    }

    public void agregarJugador(Jugador jugador) {
        listaJugadores.add(jugador);
    }

    public String mostrarJugadores() {

        StringBuffer sb = new StringBuffer();

        for (Jugador jugador : listaJugadores) {
            sb.append(jugador);
        }

        return sb.toString();
    }


    public void calcularIndiceGanador() {
        for (Jugador j : listaJugadores) {
            calculaScore(j.getDados());
        }
        listaJugadores.sort((n1, n2) -> n2.getPuntos()-n1.getPuntos());
    }



    public Score calculaScore(ArrayList<Dado> dados) {

        StringBuffer combinacion = new StringBuffer();
        String descripcion = "";
        int lugar;
        int puntos = 0;
        int monedas = 0;

        dados.sort((d1, d2) -> d1.getValor() - d2.getValor());

        for (Dado dado : dados) {
            combinacion.append(dado.getValor() + ",");
        }

        switch (combinacion.toString()) {
            case "4,5,6":
                descripcion = "Zanzibar ";
                puntos = 1000;
                lugar = 1;
                monedas = 4;
                break;
            case "1,2,3":
                descripcion = "escalera ";
                puntos = 1000;
                lugar = 2;
                monedas = 4;
                break;
            case "1,1,1", "2,2,2", "3,3,3", "4,4,4", "5,5,5", "6,6,6":
                descripcion = "tercia ";
                puntos = 500;
                lugar = 3;
                monedas = 3;
                break;

            default:
                monedas = obtenerMonedas(combinacion.toString());
                descripcion = "suma de puntos ";
                for (Dado dado : dados) {
                    puntos += dado.getValor();
                }
                // puntos = 10;
                lugar = 4;
                break;
        }

        return new Score(lugar, puntos, monedas, combinacion.toString(), descripcion);

    }

    /*
    1 = 100 puntos
    6 = 60 puntos
    2 = 2 puntos
    3 = 3 puntos
    4 = 4 puntos
    5 = 5 puntos

     */
    public int obtenerMonedas(String combinacion) {
        int m = 0;

        for (int i = 0; i < combinacion.length(); i++) {
            switch (combinacion.charAt(i)) {
                case '1':
                    m += 100;
                    break;
                case '2', '3', '4', '5':
                    m += Integer.parseInt(combinacion.charAt(i) + "");
                    break;

                case '6':
                    m += 60;
                    break;
            }
        }
        return m;
    }

    public ArrayList<Jugador> obtenerJugadores() {
        return listaJugadores;
    }


}
