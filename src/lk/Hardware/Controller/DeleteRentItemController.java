/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class DeleteRentItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtNAME;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Removebtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
    }

    @FXML
    private void Removebtn_OnAction(ActionEvent event) {
    }
    
}
