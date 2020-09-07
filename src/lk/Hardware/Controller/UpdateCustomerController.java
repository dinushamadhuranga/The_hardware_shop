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
import javafx.scene.layout.AnchorPane;
import lk.Hardware.DB.DBConnection;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class UpdateCustomerController implements Initializable {

    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtNAME;
    @FXML
    private JFXTextField txtTEL;
    @FXML
    private JFXTextField txtEMAIL;
    @FXML
    private JFXTextField txtADDRESS;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXButton updatebtn;
    @FXML
    private JFXButton showbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updatebtn.setDisable(true);
        Cancelbtn.setDisable(true);
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        ClearAll();
        txtID.setEditable(true);
        showbtn.setDisable(false);
        Cancelbtn.setDisable(true);
        updatebtn.setDisable(true);

    }

    @FXML
    private void updatebtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="UPDATE CUSTOMER SET cname=? , ctell=? , cemail=? , caddress=? WHERE cid=?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            
            prepareStatement.setString(1, txtNAME.getText());
            prepareStatement.setString(2, txtTEL.getText());            
            prepareStatement.setString(3, txtEMAIL.getText());
            prepareStatement.setString(4, txtADDRESS.getText());            
            prepareStatement.setString(5, txtID.getText());
            
            prepareStatement.execute();
            
            new Alert(Alert.AlertType.INFORMATION, "UPDATED").showAndWait();
            showbtn.setDisable(false);
            ClearAll();
            updatebtn.setDisable(true);
            Cancelbtn.setDisable(true);
    }

    @FXML
    private void showbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        if (isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "please enter customer id or telephone number to show details").showAndWait();
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting customer's id or telephone to get details").showAndWait();
                return;
            }     
        }
            showbtn.setDisable(true);
            
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

                    txtNAME.setText(rs.getString("cname"));
                    txtEMAIL.setText(rs.getString("cemail"));
                    txtADDRESS.setText(rs.getString("caddress"));
                    txtID.setText(rs.getString("cid"));
                    txtTEL.setText(String.valueOf(rs.getString("ctell")));
                    txtID.setEditable(false);
                   
                    updatebtn.setDisable(false);
                    Cancelbtn.setDisable(false);
                    showbtn.setDisable(true);
                } else {
                      return true;
        }
        return false;
    }        

    private void ClearAll() {
        txtID.clear();
        txtNAME.clear();
        txtTEL.clear();
        txtEMAIL.clear();
        txtADDRESS.clear();
    }
        
}
    

