package farklepractica.vista;

import java.util.ArrayList;

import farklepractica.controlador.JuegoFarkle;
import farklepractica.modelo.Dado;
import farklepractica.modelo.Jugador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Vista no ejecuta reglas ni nada de eso.
 * Tiene dos pantallas dentro de la misma ventana, la de elegir jugadores y la de elegir bots
 */
public class VistaFarkle {

    private static final String[] CARAS = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};

    private static final String ESTILO_BASE = "-fx-font-size: 56px; -fx-border-color: black; "
            + "-fx-border-radius: 10; -fx-background-radius: 10; ";
    private static final String ESTILO_NORMAL = ESTILO_BASE + "-fx-background-color: white;";
    private static final String ESTILO_SELECCIONADO = ESTILO_BASE + "-fx-background-color: gold;";
    private static final String ESTILO_APARTADO = ESTILO_BASE
            + "-fx-background-color: lightgray; -fx-opacity: 0.6;";

    private JuegoFarkle juego;
    private BorderPane raiz;

    // Estas son las variables de la pantalla de seleccion de jugadores y bots
    private Label etiquetaPregunta;
    private ArrayList<Button> botonesSeleccion;

    // Estas son las variables de la pantalla del juego normal
    private Label etiquetaMensaje;
    private Label etiquetaTurno;
    private ArrayList<Button> botonesDados;
    private ArrayList<Label> etiquetasJugadores;
    private Button botonTirar;
    private Button botonPlantarse;

    public VistaFarkle() {
        raiz = new BorderPane();
        crearPantallaSeleccion();
    }

    // Esta sección es de la pantalla de seleccion, donde eliges o humanos o bots

    private void crearPantallaSeleccion() {
        etiquetaPregunta = new Label();
        etiquetaPregunta.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        botonesSeleccion = new ArrayList<>();
        HBox cajaBotones = new HBox(15);
        cajaBotones.setAlignment(Pos.CENTER);
        for (int i = 0; i <= 4; i = i + 1) {
            Button boton = new Button(String.valueOf(i));
            boton.setPrefSize(80, 80);
            boton.setStyle("-fx-font-size: 28px;");
            botonesSeleccion.add(boton);
            cajaBotones.getChildren().add(boton);
        }

        VBox cajaSeleccion = new VBox(30, etiquetaPregunta, cajaBotones);
        cajaSeleccion.setAlignment(Pos.CENTER);
        raiz.setCenter(cajaSeleccion);
    }

    public void mostrarPregunta(String texto) {
        etiquetaPregunta.setText(texto);
    }

    //Esta sección es la del juego, donde 

    // Aqui se cambia la pantalla (la del menu de elegir jugadores y bots a lo del juego)
    public void mostrarJuego(JuegoFarkle juego) {
        this.juego = juego;

        etiquetaTurno = new Label();
        etiquetaTurno.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        etiquetaMensaje = new Label();
        etiquetaMensaje.setStyle("-fx-font-size: 15px;");
        etiquetaMensaje.setWrapText(true);

        VBox cajaArriba = new VBox(8, etiquetaTurno, etiquetaMensaje);
        cajaArriba.setPadding(new Insets(15));

        botonesDados = new ArrayList<>();
        HBox cajaDados = new HBox(12);
        cajaDados.setAlignment(Pos.CENTER);
        for (int i = 0; i < juego.getDados().size(); i = i + 1) {
            Button boton = new Button();
            boton.setPrefSize(95, 95);
            botonesDados.add(boton);
            cajaDados.getChildren().add(boton);
        }

        //Seccion de puntaje
        etiquetasJugadores = new ArrayList<>();
        VBox cajaPuntajes = new VBox(10);
        cajaPuntajes.setPadding(new Insets(15));
        cajaPuntajes.setMinWidth(190);
        Label titulo = new Label("Puntajes");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        cajaPuntajes.getChildren().add(titulo);
        for (int i = 0; i < juego.getJugadores().size(); i = i + 1) {
            Label etiqueta = new Label();
            etiqueta.setStyle("-fx-font-size: 15px;");
            etiquetasJugadores.add(etiqueta);
            cajaPuntajes.getChildren().add(etiqueta);
        }

        //Seccion de botones inferiores
        botonTirar = new Button("Tirar");
        botonPlantarse = new Button("Plantarse");
        botonTirar.setStyle("-fx-font-size: 18px;");
        botonPlantarse.setStyle("-fx-font-size: 18px;");
        HBox cajaBotones = new HBox(20, botonTirar, botonPlantarse);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.setPadding(new Insets(20));

        raiz.setTop(cajaArriba);
        raiz.setLeft(cajaPuntajes);
        raiz.setCenter(cajaDados);
        raiz.setBottom(cajaBotones);
    }

    // Lee el modelo y refresca todo lo que se ve
    public void actualizar() {
        ArrayList<Dado> dados = juego.getDados();
        for (int i = 0; i < dados.size(); i = i + 1) {
            Dado dado = dados.get(i);
            Button boton = botonesDados.get(i);

            boton.setText(CARAS[dado.getValor() - 1]);

            if (dado.isApartado() == true) {
                boton.setStyle(ESTILO_APARTADO);
            } else if (dado.isSeleccionado() == true) {
                boton.setStyle(ESTILO_SELECCIONADO);
            } else {
                boton.setStyle(ESTILO_NORMAL);
            }
        }

        ArrayList<Jugador> jugadores = juego.getJugadores();
        for (int i = 0; i < jugadores.size(); i = i + 1) {
            Jugador jugador = jugadores.get(i);
            String marca = "   ";
            if (i == juego.getTurnoActual() && juego.isJuegoTerminado() == false) {
                marca = "-> ";
            }
            etiquetasJugadores.get(i).setText(marca + jugador.getNombre() + ": " + jugador.getPuntos());
        }

        if (juego.isJuegoTerminado() == true) {
            etiquetaTurno.setText("Juego terminado");
        } else {
            etiquetaTurno.setText("Turno de " + juego.jugadorActual().getNombre()
                    + "  |  Puntos del turno: " + juego.getPuntosTurno());
        }
        etiquetaMensaje.setText(juego.getMensaje());

        boolean turnoDeHumano = juego.jugadorActual().esHumano() && juego.isJuegoTerminado() == false;
        botonTirar.setDisable(turnoDeHumano == false);
        botonPlantarse.setDisable(turnoDeHumano == false);
    }

    //getters

    public BorderPane getRaiz() {
        return raiz;
    }

    public ArrayList<Button> getBotonesSeleccion() {
        return botonesSeleccion;
    }

    public ArrayList<Button> getBotonesDados() {
        return botonesDados;
    }

    public Button getBotonTirar() {
        return botonTirar;
    }

    public Button getBotonPlantarse() {
        return botonPlantarse;
    }
}