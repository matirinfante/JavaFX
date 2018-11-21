/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAVAEFEXS;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.management.Notification;
import org.controlsfx.control.Notifications;

public class FXMLDocumentController {

    @FXML
    private Text boton;

    @FXML
    private TextField passwordField;

    private int contador = 0;
    String s;
    String mensaje;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        // contador++;
        TextField n = new TextField();
        s = passwordField.getText();
        System.out.println(s);
        if (!s.isEmpty()) {
            if (s.matches("[a-zA-Z]+")) {
                mensaje = "Esta todo pillo ameo";
                boton.setFill(Color.GREEN);
                Notifications.create().title("TOP TEXT").text("BOTTOM TEXT").hideCloseButton().graphic(n).position(Pos.CENTER).show();
                
            } else {
                mensaje = "EQUIVOCADO MAQUINOLA SOLO LETRA";
                boton.setFill(Color.RED);
            }

        } else {
            mensaje = " NO ESCRIBISTE NADA";
            boton.setFill(Color.DARKGREEN);
        }
        boton.setText(mensaje);

    }

    @FXML
    protected void RESET(ActionEvent e1) {
        Stage fuente = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        Scene escena = cambiarPantalla();
        fuente.setScene(escena);

        fuente.setTitle("LOGUIADO");
        fuente.setScene(escena);
        fuente.show();

    }

    private Scene cambiarPantalla() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            scene = new Scene(root, 300, 275);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

}
