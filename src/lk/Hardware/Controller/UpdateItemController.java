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
public class UpdateItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXTextField txtNAME;
    @FXML
    private JFXTextField txtUnitePrice;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXButton Updatebtn;
    @FXML
    private JFXButton showbtn;
    @FXML
    private JFXTextField txtqtyOnhand;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDescription.setDisable(true);
        txtNAME.setDisable(true);
        txtUnitePrice.setDisable(true);
        txtqtyOnhand.setDisable(true);
        
        Updatebtn.setDisable(true);
        Cancelbtn.setDisable(true);
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        ClearAll();
        txtID.setEditable(true);
        showbtn.setDisable(false);
        Cancelbtn.setDisable(true);
        Updatebtn.setDisable(true);
        
    }

    @FXML
    private void Updatebtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="UPDATE items SET iname=? , unitprice=? , quantityOnHand=? , description=? WHERE iid=?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            
            prepareStatement.setString(1, txtNAME.getText());
            prepareStatement.setDouble(2, Double.parseDouble(txtUnitePrice.getText()));            
            prepareStatement.setInt(3, Integer.parseInt(txtqtyOnhand.getText()));
            prepareStatement.setString(4, txtDescription.getText());            
            prepareStatement.setString(5, txtID.getText());
            
            prepareStatement.execute();
            
            new Alert(Alert.AlertType.INFORMATION, "UPDATED").showAndWait();
            showbtn.setDisable(false);
            ClearAll();
            Updatebtn.setDisable(true);
            Cancelbtn.setDisable(true);
            txtID.setEditable(true);
        txtDescription.setDisable(true);
        txtNAME.setDisable(true);
        txtUnitePrice.setDisable(true);
        txtqtyOnhand.setDisable(true);            
    }

    @FXML
    private void Showbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        if (isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "please enter item id to show details").showAndWait();
            return;
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting item's id to get details").showAndWait();
                return;
            }     
        }
        txtDescription.setDisable(false);
        txtNAME.setDisable(false);
        txtUnitePrice.setDisable(false);
        txtqtyOnhand.setDisable(false);        
        
            showbtn.setDisable(true);
            Updatebtn.setDisable(false);
            
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

                    txtNAME.setText(rs.getString("iname"));
                    txtUnitePrice.setText(rs.getString("unitprice"));
                    txtDescription.setText(rs.getString("description"));
                    txtID.setText(rs.getString("iid"));
                    txtqtyOnhand.setText(String.valueOf(rs.getString("quantityOnHand")));
                    txtID.setEditable(false);
                   
                    Updatebtn.setDisable(false);
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
        txtDescription.clear();
        txtUnitePrice.clear();
        txtqtyOnhand.clear();
    }
    
}
