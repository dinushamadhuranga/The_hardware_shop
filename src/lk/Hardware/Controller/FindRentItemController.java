/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.Hardware.DB.DBConnection;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class FindRentItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Findbtn;
    @FXML
    private Label lblNAME;
    @FXML
    private Label lblDESCRIPTION;
    @FXML
    private Label lblPRICE;
    @FXML
    private Label lblQTYONHAND;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Cancelbtn.setDisable(false);
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        lblDESCRIPTION.setText("");
        lblNAME.setText("");
        lblPRICE.setText("");
        lblQTYONHAND.setText("");
        txtID.clear();
        txtID.setEditable(true);
        
        Cancelbtn.setDisable(true);    
        Findbtn.setDisable(false);
        
    }

    @FXML
    private void Findbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter item id").showAndWait();
            return;
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting item's id").showAndWait();
                return;
            } 
        }
        Cancelbtn.setDisable(false);
        Findbtn.setDisable(true);
            
    }

    private boolean isEmpty() {
        return (txtID.getText().trim().isEmpty());
    }

    private boolean isValid() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="select * from rentitems where riid=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtID.getText());        
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    lblNAME.setText(rs.getString("riname"));
                    lblDESCRIPTION.setText(rs.getString("description"));
                    lblQTYONHAND.setText(rs.getString("quantityOnHand"));
                    lblPRICE.setText(rs.getString("unitperdayprice"));
                    txtID.setText(rs.getString("riid"));
                    txtID.setEditable(false);
                } else {
                      return true;
        }
        return false;
    }        
    
}
