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
public class HomeController implements Initializable {

    @FXML
    private JFXButton MenuCustomer;
    @FXML
    private JFXButton Menuitems;
    @FXML
    private JFXButton MenuRent;
    @FXML
    private JFXButton MenuOrder;
    @FXML
    private JFXButton MenuSettings;
    @FXML
    private BorderPane MenuBorderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void MenuCustomer_OnAction(ActionEvent event) {
        LoadPane("View/CustomerMenu");
    }

    @FXML
    private void Menuitems_OnAction(ActionEvent event) {
        LoadPane("View/ItemMenu");
    }

    @FXML
    private void MenuRent_OnAction(ActionEvent event) {
        LoadPane("View/Rent");
    }

    @FXML
    private void MenuOrder_OnAction(ActionEvent event) {
        LoadPane("View/Order");
    }

    @FXML
    private void MenuSettings_OnAction(ActionEvent event) {
        LoadPane("View/HomePage");
    }
    
    private void LoadPane(String ui){
        Parent root = null;
        
        try {
            root=FXMLLoader.load(Main.class.getResource(ui+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MenuBorderPane.getChildren().clear();
        MenuBorderPane.setCenter(root);
    }
    
}
