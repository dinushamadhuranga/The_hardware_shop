/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Customer;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class LoadCustomerController implements Initializable {

    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> IdColumn;
    @FXML
    private TableColumn<Customer, String> NameColumn;
    @FXML
    private TableColumn<Customer, Integer> TelephoneColumn;
    @FXML
    private TableColumn<Customer, String> EmailColumn;
    @FXML
    private TableColumn<Customer, String> AddressColumn;

    ObservableList<Customer> oblist = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="select * from customer";
            
            ResultSet rs=connection.createStatement().executeQuery(sql);
            
            while (rs.next()) {                
                oblist.add(new Customer(rs.getString("cid"), rs.getString("cname"), rs.getInt("ctell"), rs.getString("cemail"), rs.getString("caddress")));
            }
            
            
            IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
            EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            
            CustomerTable.setItems(oblist);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CustomerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                Customer value = observable.getValue();
                System.out.println(value.getName());
            }
        });
        
    }    
    
}
