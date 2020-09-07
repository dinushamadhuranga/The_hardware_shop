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
public class AddRentItemController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtNAME;
    @FXML
    private JFXTextField txtPerdauPrice;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private JFXButton Addbtn;
    @FXML
    private JFXTextField txtQuantity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setItemid();
            txtID.setDisable(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddRentItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddRentItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
    }

    @FXML
    private void Addbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please fill all fields").showAndWait();
        } else {
            
            Connection connection =DBConnection.getinstance().getConnection();
            String sql="INSERT INTO rentitems VALUES(?,?,?,?,?)";
            
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, txtID.getText());
            prepareStatement.setString(2, txtNAME.getText());
            prepareStatement.setDouble(3, Double.parseDouble(txtPerdauPrice.getText()));
            prepareStatement.setInt(4, Integer.parseInt(txtQuantity.getText()));
            prepareStatement.setString(5, txtDescription.getText());
            
            prepareStatement.execute();
            new Alert(Alert.AlertType.INFORMATION, "Item Added").showAndWait();
            txtDescription.clear();
            txtNAME.clear();
            txtID.clear();
            txtPerdauPrice.clear();
            txtQuantity.clear();
            
            setItemid();
        }
    }

    private boolean isEmpty() {
        return txtQuantity.getText().trim().isEmpty()||txtNAME.getText().trim().isEmpty()||txtPerdauPrice.getText().trim().isEmpty();
    }

    private String getCustomerId() throws ClassNotFoundException, SQLException {
        Connection connection=DBConnection.getinstance().getConnection();
        String sql="select riid from rentitems order by riid desc limit 1";
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
                String []parts=id.split("ri");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="ri"+(x+1);
                    
                }else if(x>=9){
                    a="ri0"+(x+1);
                    
                }else if(x>0){
                    a="ri00"+(x+1);
                }
                id=a;
            } else {
                a="ri001";
                id=a;
            }
            txtID.setText(id);
    }     
}
