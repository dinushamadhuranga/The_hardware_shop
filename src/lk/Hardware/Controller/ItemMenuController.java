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
public class ItemMenuController implements Initializable {

    @FXML
    private BorderPane ItemMenuBorderPane;
    @FXML
    private JFXButton AddItem;
    @FXML
    private JFXButton FindItem;
    @FXML
    private JFXButton RemoveItem;
    @FXML
    private JFXButton UpdateItem;
    @FXML
    private JFXButton AddRentItem;
    @FXML
    private JFXButton FindRentItem;
    @FXML
    private JFXButton RemoveRentItem;
    @FXML
    private JFXButton UpdateRentItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadPane("View/LoadAllItems");
    }    

    @FXML
    private void AddItem_OnAction(ActionEvent event) {
        LoadPane("View/AddItem");
    }

    @FXML
    private void FindItem_OnAction(ActionEvent event) {
        LoadPane("View/FindItem");
    }


    @FXML
    private void RemoveItem_OnAction(ActionEvent event) {
        LoadPane("View/RemoveItem");
    }

    @FXML
    private void UpdateItem_OnAction(ActionEvent event) {
        LoadPane("View/UpdateItem");
    }

    @FXML
    private void AddRentItem_OnAction(ActionEvent event) {
        LoadPane("View/AddRentItem");
    }

    @FXML
    private void FindRentItem_OnAction(ActionEvent event) {
        LoadPane("View/FindRentItem");
    }

    @FXML
    private void RemoveRentItem_OnAction(ActionEvent event) {
        LoadPane("View/DeleteRentItem");
    }
    Parent root = null;
     private void LoadPane(String ui){
        //Parent root = null;
        
        try {
            root=FXMLLoader.load(Main.class.getResource(ui+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemMenuBorderPane.getChildren().clear();
        ItemMenuBorderPane.setCenter(root);
    }

    @FXML
    private void UpdateRentItem_OnAction(ActionEvent event) {
        LoadPane("View/UpdateRentItem");
    }
      
    
}
