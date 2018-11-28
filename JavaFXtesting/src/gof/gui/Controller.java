package gof.gui;

import gof.core.Tablero;
import gof.core.Tarea;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

    private final int TAMANO = 15;
    private final double DEFAULT_PROB = 0.3;

    @FXML
    private FlowPane base;
    @FXML
    private HBox presetBox;
    @FXML
    private Button runButton, stopButton, randomizeButton, clearButton;
    @FXML
    private HBox rootBox;

    private Tablero tablero;
    boolean modo = true; // En principio el modo se setea en true, es decir, en modo Verificar. False, modificar.
    int cantTareas = 2;
    ExecutorService executor = Executors.newFixedThreadPool(cantTareas);
    Set<Callable<Tarea>> tareas = new HashSet();

    private JavaFXDisplayDriver display;

    private Timeline turno = null;

    private int windowWidth = 750;
    private int cellSizePx = 30;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        crearTablero(TAMANO, DEFAULT_PROB);
        for (int i = 0; i < TAMANO; i++) {
            tareas.add(new Tarea(tablero, i, modo));
        }
      //  attachResizeListener();
    }

    @FXML
    private void play(Event evt) {
        cambiarBotones(false);

        turno = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            try {
                executor.invokeAll(tareas);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        crearTablero(TAMANO, (double) 50 / 100);
    }

    
    @FXML
    private void onAbout(Event evt) {
        // TEXT //
        Text text1 = new Text("Conway's Game of Life\n");
        text1.setFont(Font.font(30));
        Text text2 = new Text(
                "\nThe Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.\n"
                + "The game is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves or, for advanced players, by creating patterns with particular properties."
        );
        Text text3 = new Text("\n\nRules\n");
        text3.setFont(Font.font(20));
        Text text4 = new Text(
                "\nThe universe of the Game of Life is a two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:\n"
                + "\n1) Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n"
                + "2) Any live cell with two or three live neighbours lives on to the next generation.\n"
                + "3) Any live cell with more than three live neighbours dies, as if by overcrowding.\n"
                + "4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n\nMore on Wikipedia:\n"
        );

        Hyperlink link = new Hyperlink("http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life <-------not working");
        TextFlow tf = new TextFlow(text1, text2, text3, text4, link);
        tf.setPadding(new Insets(10, 10, 10, 10));
        tf.setTextAlignment(TextAlignment.JUSTIFY);
        // END TEXT, START WINDOW //
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(new Stage());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(tf);
        Scene dialogScene = new Scene(dialogVbox, 450, 500);
        dialog.setScene(dialogScene);
        dialog.show();
        // END WINDOW //
    }

    private void cambiarBotones(boolean enable) {
        runButton.setDisable(!enable);
        clearButton.setDisable(!enable);
        randomizeButton.setDisable(!enable);

        stopButton.setDisable(enable);
    }

    private void crearTablero(int tam ,double prob) {
        tablero = new Tablero(tam, tam, prob);
        crearDisplay();
        for (int i = 0; i < TAMANO; i++) {
            tareas.add(new Tarea(tablero, i, modo));
        }
    }

    private void crearDisplay() {
        display = new JavaFXDisplayDriver(tablero.getTamano(), cellSizePx, tablero);
        
        base.getChildren().clear();
        base.getChildren().add(new Group(display.obtenerRectangulo()));
    }

    /*private void attachResizeListener() {
        ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int newWidth = newValue.intValue();
                if (newWidth > 250 && Math.abs(newWidth - windowWidth) >= 50) {
                    windowWidth = newWidth;
                    cellSizePx = newWidth / 25;
                    crearDisplay();
                }
            }
        };
        rootBox.widthProperty().addListener(sizeListener);
    }*/
}
