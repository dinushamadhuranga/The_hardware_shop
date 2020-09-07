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
public class RemoveItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Removebtn;
    @FXML
    private Label lblName;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblQtyOnHand;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private Label lblDescription1;
    @FXML
    private JFXButton showbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblDescription.setDisable(true);
        lblName.setDisable(true);
        lblUnitPrice.setDisable(true);
        lblQtyOnHand.setDisable(true);
        
        Removebtn.setDisable(true);
        Cancelbtn.setDisable(true);        
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        ClearAll();
        txtID.setEditable(true);
        showbtn.setDisable(false);
        Cancelbtn.setDisable(true);
        Removebtn.setDisable(true);
                
    }

    @FXML
    private void Removebtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="DELETE FROM ITEMS WHERE iid=?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            
            prepareStatement.setString(1, txtID.getText());
            new Alert(Alert.AlertType.WARNING, "you cant get again this item").showAndWait();
            prepareStatement.execute();
            
            new Alert(Alert.AlertType.INFORMATION, "Deleted").showAndWait();
            showbtn.setDisable(false);
            ClearAll();
            Removebtn.setDisable(true);
            Cancelbtn.setDisable(true);
            txtID.setEditable(true);
        lblDescription.setDisable(true);
        lblName.setDisable(true);
        lblUnitPrice.setDisable(true);
        lblQtyOnHand.setDisable(true);            
    }

    @FXML
    private void showbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        if (isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "please enter item id to show details").showAndWait();
            return;
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting item's id to get details").showAndWait();
                return;
            }     
        }     
        
            showbtn.setDisable(true);
            Removebtn.setDisable(false);
            
    }

    private boolean isEmpty() {
        return (txtID.getText().trim().isEmpty());
    }

    private boolean isValid() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="select * from items where iid=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtID.getText());
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    lblName.setText(rs.getString("iname"));
                    lblUnitPrice.setText(rs.getString("unitprice"));
                    lblDescription.setText(rs.getString("description"));
                    txtID.setText(rs.getString("iid"));
                    lblQtyOnHand.setText(String.valueOf(rs.getString("quantityOnHand")));
                    txtID.setEditable(false);
                   
                    Removebtn.setDisable(false);
                    Cancelbtn.setDisable(false);
                    showbtn.setDisable(true);
                } else {
                      return true;
        }
        return false;
    }        

    private void ClearAll() {
        txtID.clear();
        lblName.setText("");
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
    }

}
