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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Item;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class LoadAllItemsController implements Initializable {

    @FXML
    private TableColumn<Item, String> IdColumn;
    @FXML
    private TableColumn<Item, String> NameColumn;
    @FXML
    private TableColumn<Item, Double> UnitpriceColumn;
    @FXML
    private TableColumn<Item, Integer> qtyColumn;
    @FXML
    private TableColumn<Item, String> DesciptionColumn;
    @FXML
    private TableView<Item> ItemTable;
    
    ObservableList<Item> oblist = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="select * from items";
            
            ResultSet rs=connection.createStatement().executeQuery(sql);
            
            while (rs.next()) {                
                oblist.add(new Item(rs.getString("iid"), rs.getString("iname"), rs.getDouble("unitprice"), rs.getInt("quantityonhand"), rs.getString("description")));
            }
            
            
            IdColumn.setCellValueFactory(new PropertyValueFactory<>("itemid"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("itemname"));
            UnitpriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
            qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qtyonhand"));
            DesciptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemdescription"));
            
            ItemTable.setItems(oblist);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }   
    
    
    
    
    
}
