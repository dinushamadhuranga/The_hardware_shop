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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Customer;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class FindCustomerController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtTEL;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Savebtn;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cancelbtn.setDisable(true);
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        lblAddress.setText("");
        lblName.setText("");
        lblEmail.setText("");
        txtID.clear();
        txtTEL.clear();
        txtID.setEditable(true);
        txtTEL.setEditable(true);
        Cancelbtn.setDisable(true);
    }

    @FXML
    private void Savebtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter customer id or telephone number").showAndWait();
            return;
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting customer's id or telephone").showAndWait();
                return;
            } 
        }
        Cancelbtn.setDisable(false);
            
    }

    private boolean isEmpty() {
        return (txtID.getText().trim().isEmpty() && txtTEL.getText().trim().isEmpty());
    }

    private boolean isValid() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="select * from customer where cid=? || ctell=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtID.getText());
                prepareStatement.setString(2, txtTEL.getText());        
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    lblName.setText(rs.getString("cname"));
                    lblEmail.setText(rs.getString("cemail"));
                    lblAddress.setText(rs.getString("caddress"));
                    txtID.setText(rs.getString("cid"));
                    txtTEL.setText(String.valueOf(rs.getString("ctell")));
                    txtID.setEditable(false);
                    txtTEL.setEditable(false);
                } else {
                      return true;
        }
        return false;
    }
        
}
    
