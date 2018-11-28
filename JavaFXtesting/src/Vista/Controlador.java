package Vista;

import JuegoNucleo.Tablero;
import JuegoNucleo.Tarea;

import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controlador implements Initializable {

    private final int TAMANO = 15;
    private final double DEFAULT_PROB = 0.3;

    @FXML
    private FlowPane base;

    @FXML
    private Button botonPlay, botonPausa, botonRandom, botonLimpiar;

    private Tablero tablero;
    boolean modo = true; // En principio el modo se setea en true, es decir, en modo Verificar. False, modificar.
    int cantTareas = 2;
    ExecutorService executor = Executors.newFixedThreadPool(cantTareas);
    Set<Callable<Tarea>> tareas = new HashSet();

    private DisplayTablero display;

    private Timeline turno = null;

    private int cellSizePx = 35;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        crearTablero(TAMANO, DEFAULT_PROB);
        for (int i = 0; i < TAMANO; i++) {
            tareas.add(new Tarea(tablero, i, modo));
        }
     
    }

    @FXML
    private void play(Event evt) {
        cambiarBotones(false);

        turno = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            try {
                executor.invokeAll(tareas);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            modo = !modo;
            for (Callable<Tarea> tarea : tareas) {
                ((Tarea) tarea).setModo(modo);
            }
            if (!modo) {
                display.mostrarTablero(tablero);
            }
        }));

        turno.setCycleCount(Timeline.INDEFINITE);
        turno.play();
    }

    @FXML
    private void pausa(Event evt) {
        cambiarBotones(true);
        turno.stop();
    }

    @FXML
    private void limpiarTablero(Event evt) {
        crearTablero(TAMANO, 0);
    }

    @FXML
    private void randomizar(Event evt) {
        Random r = new Random();
        crearTablero(TAMANO, (double) r.nextInt(50) / 100);
    }

    @FXML
    private void instrucciones(Event evt) {
        Text text1 = new Text("Juego de la Vida\n");
        text1.setFont(Font.font(30));
        Text text2 = new Text(
                "\nImplementación del juego de la vida de Conway con uso de concurrencia.\n"
                + "Para empezar la simulación, click en EMPEZAR. Para pausar la simulación, click en PAUSA.\nPara generar celulas aleatoriamente, click en ALEATORIO. Para limpiar el tablero, click en LIMPIAR."
        );
        Text about = new Text("\nInfante, Matias - Morales, Matias - Lillo, Martin // Estudiantes Facultad de Informática Universidad Nacional del Comahue");
        about.setFont(Font.font(20));
        Text text3 = new Text("\nRepositorio en Github: \n");
        text3.setFont(Font.font(20));
        Hyperlink link = new Hyperlink("https://github.com/matirinfante/JavaFX");
        TextFlow tf = new TextFlow(text1, text2, about, text3, link);
        tf.setPadding(new Insets(10, 10, 10, 10));
        tf.setTextAlignment(TextAlignment.JUSTIFY);

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(new Stage());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(tf);
        Scene dialogScene = new Scene(dialogVbox, 450, 500);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    private void cambiarBotones(boolean enable) {
        botonPlay.setDisable(!enable);
        botonLimpiar.setDisable(!enable);
        botonRandom.setDisable(!enable);

        botonPausa.setDisable(enable);
    }

    private void crearTablero(int tam, double prob) {
        tablero = new Tablero(tam, tam, prob);
        crearDisplay();
        for (int i = 0; i < TAMANO; i++) {
            tareas.add(new Tarea(tablero, i, modo));
        }
    }

    private void crearDisplay() {
        display = new DisplayTablero(tablero.getTamano(), cellSizePx, tablero);
        base.getChildren().clear();
        base.getChildren().add(new Group(display.obtenerRectangulo()));
    }
    public void shutdown(){
        executor.shutdown();
    }
}
