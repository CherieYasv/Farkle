package com.farkle.Modelo;

import java.util.ArrayList;

public class Puntuacion {
    /**
     * Metodo para validar si al lanzar el dado sale alguno de estos valores, los cuales son menos probables pero mas
     * interactuvimos*/
    public static int calcular(ArrayList<Integer> valores) {
        int[] conteo = contar(valores);
        int total = 0;

        if (valores.size() == 6) {
            if (esEscalera(conteo) == true) {
                return 1500;
            }
            if (sonDosTercias(conteo) == true) {
                return 2500;
            }
            if (esCuatroConPar(conteo) == true) {
                return 1500;
            }
            if (sonTresPares(conteo) == true) {
                return 1500;
            }
        }

        //Si no se cumple los valores de arriba, da continuidad para determianar el puntaje
        for (int valor = 1; valor <= 6; valor = valor + 1) {
            int cantidad = conteo[valor];

            if (cantidad >= 3) {
                total = total + puntosIguales(valor, cantidad);
            } else if (cantidad > 0) {
                if (valor == 1) {
                    total = total + (cantidad * 100);
                } else if (valor == 5) {
                    total = total + (cantidad * 50);
                } else {
                    return 0;
                }
            }
        }

        return total;
    }

    private static final String[] NOMBRES = {"", "Un", "Dos", "Tres", "Cuatro", "Cinco", "Seis"};

    //Ciclo que divide cuantas valores de cada cada tiene, si a 2 de 1, 4 de 2 y asi.
    public static int[] contar(ArrayList<Integer> valores) {
        int[] conteo = new int[7];
        for (int valor : valores) {
            conteo[valor] = conteo[valor] + 1;
        }
        return conteo;
    }

    // Cuántos números distintos aparecen exactamente "cantidad" de veces
    private static int cuantosTienen(int[] conteo, int cantidad) {
        int total = 0;
        for (int valor = 1; valor <= 6; valor = valor + 1) {
            if (conteo[valor] == cantidad) {
                total = total + 1;
            }
        }
        return total;
    }

    private static boolean esEscalera(int[] conteo) {
        return cuantosTienen(conteo, 1) == 6;
    }

    private static boolean sonDosTercias(int[] conteo) {
        return cuantosTienen(conteo, 3) == 2;
    }

    private static boolean esCuatroConPar(int[] conteo) {
        return cuantosTienen(conteo, 4) == 1 && cuantosTienen(conteo, 2) == 1;
    }

    private static boolean sonTresPares(int[] conteo) {
        return cuantosTienen(conteo, 2) == 3;
    }

    // Puntos de tres o más dados iguales
    private static int puntosIguales(int valor, int cantidad) {
        if (cantidad == 6) {
            return 3000;
        }
        if (cantidad == 5) {
            return 2000;
        }
        if (cantidad == 4) {
            return 1000;
        }
        if (valor == 1) {
            return 300;
        }
        return valor * 100;
    }

    //Se encarga de validar que en cada ronda los valores se muestren la validacion de que se ejecuto una combinacion, si no, regresara que no se hizo
    public static boolean hayPuntos(ArrayList<Integer> valores) {
        int[] conteo = contar(valores);

        if (conteo[1] > 0 || conteo[5] > 0) {
            return true;
        }

        for (int valor = 1; valor <= 6; valor = valor + 1) {
            if (conteo[valor] >= 3) {
                return true;
            }
        }

        // Escalera o tres pares (solo se dan con 6 dados)
        if (valores.size() == 6 && calcular(valores) > 0) {
            return true;
        }

        return false;
    }

    //Conteo para describir las combinaciones mas dificiles
    public static String describir(ArrayList<Integer> valores) {
        int[] conteo = contar(valores);

        if (valores.size() == 6) {
            if (esEscalera(conteo) == true) {
                return "Escalera";
            }
            if (sonDosTercias(conteo) == true) {
                return "Dos tercias";
            }
            if (esCuatroConPar(conteo) == true) {
                return "Cuatro iguales con par";
            }
            if (sonTresPares(conteo) == true) {
                return "Tres pares";
            }
        }

        ArrayList<String> partes = new ArrayList<>();
        for (int valor = 1; valor <= 6; valor = valor + 1) {
            int cantidad = conteo[valor];

            if (cantidad > 0) {
                String texto = NOMBRES[cantidad] + " " + valor;
                if (cantidad > 1) {
                    texto = texto + "s";
                }
                partes.add(texto);
            }
        }

        return String.join(" + ", partes);
    }
}
