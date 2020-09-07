/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Main;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private Hyperlink forgotpassword;
    @FXML
    private JFXButton signAdministrator;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(null);
        password.setText(null);
    }    

    @FXML
    private void login_OnAction(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        
        if (Checkuser()) {
            return;
        }        
        Parent root = FXMLLoader.load(Main.class.getResource("View/Home.fxml"));
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

        Stage window = (Stage) login.getScene().getWindow();
        window.close();
    }

    private boolean Checkuser() throws ClassNotFoundException, SQLException {
        if (isValid()) {
            new Alert(Alert.AlertType.INFORMATION, "please insert password and username").showAndWait();
            return true;
        }

        Connection connection=DBConnection.getinstance().getConnection();
        String sql="select * from login where username=? && password=?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, username.getText());
        prepareStatement.setString(2, password.getText());
        ResultSet executeQuery = prepareStatement.executeQuery();
        password.setText(null);
        if (executeQuery.next()) {
            return false;
        } else {
            new Alert(Alert.AlertType.INFORMATION, "please check your username and password").showAndWait();
            return true;
        }
    }

    private boolean isValid() {
        return password.getText()==null || username.getText()==null;
    }

}
