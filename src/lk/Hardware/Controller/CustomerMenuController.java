/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import lk.Hardware.Main;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class CustomerMenuController implements Initializable {

    @FXML
    private JFXButton AddCustomer;
    @FXML
    private JFXButton FindCustomer;
    @FXML
    private JFXButton UpdateCustomer;
    @FXML
    private JFXButton DeleteCustomer;
    @FXML
    private BorderPane CustomerMenuBorderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadPane("View/LoadCustomer");
    }    

    @FXML
    private void AddCustomer_OnAction(ActionEvent event) {
        LoadPane("View/AddCustomer");
    }

    @FXML
    private void FindCustomer_OnAction(ActionEvent event) {
        LoadPane("View/FindCustomer");
    }

    @FXML
    private void UpdateCustomer_OnAction(ActionEvent event) {
        LoadPane("View/UpdateCustomer");
    }

    @FXML
    private void DeleteCustomer_OnAction(ActionEvent event) {
        LoadPane("View/DeleteCustomer");
    }
    
    private void LoadPane(String ui){
        Parent root = null;
        
        try {
            root=FXMLLoader.load(Main.class.getResource(ui+".fxml"));
            System.gc();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CustomerMenuBorderPane.getChildren().clear();
        CustomerMenuBorderPane.setCenter(root);
    }
        
}
