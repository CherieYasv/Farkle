package farklepractica;

import farklepractica.controlador.ControladorFarkle;
import farklepractica.vista.VistaFarkle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ventana) {
        VistaFarkle vista = new VistaFarkle();
        new ControladorFarkle(vista);

        ventana.setTitle("Farkle");
        ventana.setScene(new Scene(vista.getRaiz(), 850, 480));
        ventana.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}