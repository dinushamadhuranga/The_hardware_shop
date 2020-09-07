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
import javafx.scene.layout.AnchorPane;
import lk.Hardware.DB.DBConnection;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class AddItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtNAME;
    @FXML
    private JFXTextField txtUnitePrice;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Addbtn;
    @FXML
    private JFXTextField txtquantity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setItemid();
            txtID.setDisable(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
    }

    @FXML
    private void Addbtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {

        
        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please fill all fields").showAndWait();
        } else {
            
            Connection connection =DBConnection.getinstance().getConnection();
            String sql="INSERT INTO items VALUES(?,?,?,?,?)";
            
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, txtID.getText());
            prepareStatement.setString(2, txtNAME.getText());
            prepareStatement.setDouble(3, Double.parseDouble(txtUnitePrice.getText()));
            prepareStatement.setInt(4, Integer.parseInt(txtquantity.getText()));
            prepareStatement.setString(5, txtDescription.getText());
            
            prepareStatement.execute();
            new Alert(Alert.AlertType.INFORMATION, "Item Added").showAndWait();
            txtDescription.clear();
            txtNAME.clear();
            txtID.clear();
            txtUnitePrice.clear();
            txtquantity.clear();
            
            setItemid();
        }
    }

    private boolean isEmpty() {
        return txtquantity.getText().trim().isEmpty()||txtNAME.getText().trim().isEmpty()||txtUnitePrice.getText().trim().isEmpty();
    }

    private String getCustomerId() throws ClassNotFoundException, SQLException {
        Connection connection=DBConnection.getinstance().getConnection();
        String sql="select iid from items order by iid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }

    private void setItemid() throws ClassNotFoundException, SQLException {
        
        String id=getCustomerId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("i");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>10) {
                    a="i0"+(x+1);
                    
                }else if(x>100){
                    a="i"+(x+1);
                    
                }else if(x>0){
                    a="i00"+(x+1);
                }
                id=a;
            } else {
                a="i001";
                id=a;
            }
            txtID.setText(id);
    }
        
    private void Itemqty() throws ClassNotFoundException, SQLException {
        
        String id=getCustomerId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("I");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>10) {
                    a="I0"+(x+1);
                    
                }else if(x>100){
                    a="I"+(x+1);
                    
                }else if(x>0){
                    a="I00"+(x+1);
                }
                id=a;
            } else {
                a="I001";
                id=a;
            }
            txtID.setText(id);
    }    
    
}
