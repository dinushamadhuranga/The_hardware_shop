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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DeleteCustomerController implements Initializable {

    @FXML
    private JFXTextField txtID;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtTEL;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Deletebtn;
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
        
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        
    }

    @FXML
    private void Deletebtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter customer id or telephone number").showAndWait();
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting customer's id or telephone").showAndWait();
                return;
            }
            try {
                String id=txtID.getText();
                String tel=txtTEL.getText();
                    
                //int tel=Integer.parseInt(txtTEL.getText());
                
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="Delete from customer where CId=? || ctell=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, id);
                prepareStatement.setString(2, tel);
                
                prepareStatement.execute();
                new Alert(Alert.AlertType.INFORMATION, "Deleted succesfully").showAndWait();
                
                
            } catch (ClassNotFoundException ex) {
                System.out.println("");
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "please enter valid id or tel number").showAndWait();
            }
            
        }
            
    }

    private boolean isEmpty() {
        return (txtID.getText().trim().isEmpty() && txtTEL.getText().trim().isEmpty());
    }

    private boolean isValid() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="select cid from customer where cid=? || ctell=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtID.getText());
                prepareStatement.setString(2, txtTEL.getText());        
                
                ResultSet resultSet=prepareStatement.executeQuery();
                
                if (resultSet.next()) {
            return false;
        } else {
                    return true;
        }
    }
    
}
