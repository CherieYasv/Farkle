package farklepractica.modelo;

public class JugadorHumano extends Jugador {

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esHumano() {
        return true;
    }
}
