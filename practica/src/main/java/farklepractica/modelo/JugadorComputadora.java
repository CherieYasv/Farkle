package farklepractica.modelo;

public class JugadorComputadora extends Jugador {

    public JugadorComputadora(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esHumano() {
        return false;
    }
}
