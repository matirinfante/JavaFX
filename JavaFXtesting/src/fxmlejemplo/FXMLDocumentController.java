/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlejemplo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
public class FXMLDocumentController 
{
     @FXML
     private Text actiontarget;
     @FXML
     protected void handleSubmitButtonAction(ActionEvent event)
     {
         actiontarget.setText("Sign in button pressed");
     }
}