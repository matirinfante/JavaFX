package Vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JuegoDeLaVida extends Application {

    static Controlador c;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interfaz.fxml"));
        Parent parent = loader.load();
        c = (Controlador)loader.getController();
        parent.setStyle("-fx-base: rgba(60, 60, 60, 255);");
        primaryStage.setTitle("Juego de la Vida Concurrente");
        primaryStage.setScene(new Scene(parent));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        c.shutdown();
    }

    public static void main(String[] args) {
        launch();
    }
}
