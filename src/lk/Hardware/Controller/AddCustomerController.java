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
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Customer;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class AddCustomerController implements Initializable {

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
    private JFXButton Savebtn;
    @FXML
    private AnchorPane AnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setCustomerid();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) {
        Stage stage=(Stage) AnchorPane.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void Savebtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        
        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please fill all fields").showAndWait();
        } else {
            if (checktele()) {
            new Alert(Alert.AlertType.ERROR, "please enter valid telephone number").showAndWait();    
            } else {
            
            Connection connection =DBConnection.getinstance().getConnection();
            String sql="INSERT INTO CUSTOMER VALUES(?,?,?,?,?)";
            
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, txtID.getText());
            prepareStatement.setString(2, txtNAME.getText());
            prepareStatement.setString(3, txtTEL.getText());
            prepareStatement.setString(4, txtEMAIL.getText());
            prepareStatement.setString(5, txtADDRESS.getText());
            
            prepareStatement.execute();
            new Alert(Alert.AlertType.INFORMATION, "Customer Added").showAndWait();
            txtEMAIL.clear();
            txtNAME.clear();
            txtADDRESS.clear();
            txtTEL.clear();
            setCustomerid();                
            }
        }
    }

    private boolean isEmpty() {
        return txtADDRESS.getText().trim().isEmpty()||txtNAME.getText().trim().isEmpty()||txtTEL.getText().trim().isEmpty();
    }

    private String getCustomerId() throws ClassNotFoundException, SQLException {
        Connection connection=DBConnection.getinstance().getConnection();
        String sql="select cid from customer order by cid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }

    private void setCustomerid() throws ClassNotFoundException, SQLException {
        
        String id=getCustomerId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("C");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>10) {
                    a="C0"+(x+1);
                    
                }else if(x>100){
                    a="C"+(x+1);
                    
                }else if(x>0){
                    a="C00"+(x+1);
                }
                id=a;
            } else {
                a="C001";
                id=a;
            }
            txtID.setText(id);
    }

    private boolean checktele() {
        return !(txtTEL.getText().matches("[0-9]*") && txtTEL.getText().length()==10);
    }
    
}
