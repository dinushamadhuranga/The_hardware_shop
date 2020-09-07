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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Delivery;
import lk.Hardware.Model.Item;
import lk.Hardware.Model.Order;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class OrderController implements Initializable { 

    private HBox Hbox;
    private CheckBox Checkbox;
    @FXML
    private JFXTextField txtMob;
    @FXML
    private Label lblName;
    @FXML
    private Label lblId;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private AnchorPane MiddleAnchorpane;
    @FXML
    private JFXTextField txtItemid;
    @FXML
    private JFXTextField itemqty;
    @FXML
    private TableView<Order> itemtable;
    @FXML
    private JFXButton Addbtn;
    @FXML
    private JFXButton showbtn;
    @FXML
    private JFXButton finalizebtn;
    @FXML
    private JFXButton submitbtn;
    @FXML
    private Label lblOrderid;
    @FXML
    private Label lblItemname;
    @FXML
    private Label lblItemdescription;
    @FXML
    private Label lblunitprice;
    @FXML
    private Label price;
    @FXML
    private AnchorPane DownAnchorpane;
    @FXML
    private AnchorPane Discountpane;
    @FXML
    private CheckBox disablecheckbox;
    @FXML
    private ComboBox<String> citycombobox;
    @FXML
    private Label lblcity;
    @FXML
    private Label lblamount;
    @FXML
    private JFXButton Confirmbtn;
    @FXML
    private JFXButton Cancelbtn;
    @FXML
    private Label lbltotalamount;
    @FXML
    private Label lblitemsamount;
    @FXML
    private TableColumn<Order, String> idcolumn;
    @FXML
    private TableColumn<Order, String> namecolumn;
    @FXML
    private TableColumn<Order, Integer> qtycolumn;
    @FXML
    private TableColumn<Order, Double> pricecolumn;
    
    private Double itemsamount=0.0;
    private Double deliveryamount=0.0;
    private Double totalamount=0.0;
    @FXML
    private AnchorPane Discountpane1;
    @FXML
    private Label lblcityid;
    @FXML
    private Label lbldeliveryamount;
    
    private Connection connection;
    int qty;
    int qtyonhand;

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection=DBConnection.getinstance().getConnection();
            ResetAll();
            Setcombobox();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  
    
    @FXML
    private void Addbtn_OAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (itemqty.getText().isEmpty() || !itemqty.getText().matches("[0-9]*") )  {
            new Alert(Alert.AlertType.INFORMATION, "Please enter item quantity that customer wants!").showAndWait();
            return;
        } 
        
        if (qty<Integer.parseInt(itemqty.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "You can get only "+qty+" from this item!!!").showAndWait();
            return;
        }
        
        itemsamount+=(Double.parseDouble(lblunitprice.getText())*Integer.parseInt(itemqty.getText()));
            price.setText(String.valueOf((Double.parseDouble(lblunitprice.getText())*Integer.parseInt(itemqty.getText()))));

            String sql="select * from items where iid=\""+txtItemid.getText()+"\"";
 
            ObservableList<Order> oblist = FXCollections.observableArrayList();
            ResultSet rs=connection.createStatement().executeQuery(sql);
            Item i = null;
            while (rs.next()) {                
                i=new Item(rs.getString("iid"), rs.getString("iname"), rs.getDouble("unitprice"), rs.getInt("quantityonhand"), rs.getString("description"));
            }
           
            Order order=new Order(i.getItemid(), i.getItemname(), Integer.parseInt(itemqty.getText()), Double.parseDouble(price.getText()));
            
            idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            qtycolumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
            pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            itemtable.getItems().add(order);
            Addbtn.setDisable(true);
            lblItemname.setText("");lblItemdescription.setText("");lblunitprice.setText("");price.setText("");itemqty.clear();
            finalizebtn.setDisable(false);

    }

    @FXML
    private void showbtn_onAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (isEmpty1()) {
            new Alert(Alert.AlertType.ERROR, "please enter item id").showAndWait();
            return;
        } else {    
            if (isValid1()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting items's id").showAndWait();
                return;
            } 
        }
        Addbtn.setDisable(false);
    }

    private boolean isEmpty1() {
        return (txtItemid.getText().trim().isEmpty());
    }

    private boolean isValid1() throws SQLException, ClassNotFoundException {
        
                String sql="select * from items where iid=?";
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtItemid.getText());
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {
                    qty=rs.getInt("quantityonhand");
                    lblItemname.setText(rs.getString("iname"));
                    lblItemdescription.setText(rs.getString("description"));
                    lblunitprice.setText(rs.getString("unitprice"));
                } else {
                      return true;
        }
        return false;        
    }

    @FXML
    private void finalizebtn_OnAction(ActionEvent event) {
        MiddleAnchorpane.setDisable(true);
        txtMob.setDisable(true);
        submitbtn.setDisable(true);
        lblitemsamount.setText(String.valueOf(itemsamount));
        Discountpane.setDisable(false);
        
        totalamount=itemsamount;
        lbltotalamount.setText(String.valueOf(totalamount));
        Confirmbtn.setDisable(false);
              
    }

    @FXML
    private void submitbtn_onAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter customer telephone number").showAndWait();
            return;
        } else {
            
            if (isValid()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting customer's telephone number").showAndWait();
                return;
            } 
        }
        Cancelbtn.setDisable(false);
        MiddleAnchorpane.setDisable(false);
        finalizebtn.setDisable(true);
        Addbtn.setDisable(true);
            
    }

    private boolean isEmpty() {
        return (txtMob.getText().trim().isEmpty());
    }

    private boolean isValid() throws SQLException, ClassNotFoundException {
                String sql="select * from customer where ctell=?";
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtMob.getText());        
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    lblName.setText(rs.getString("cname"));
                    lblEmail.setText(rs.getString("cemail"));
                    lblAddress.setText(rs.getString("caddress"));
                    lblId.setText(rs.getString("cid"));
                    txtMob.setEditable(false);
                } else {
                      return true;
        }
        return false;     
    }

    @FXML
    private void Confirmbtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        AddOrder();
        AddOrderdetail();
        makePayment();
        
        new Alert(Alert.AlertType.INFORMATION, "Order Successfully added").showAndWait();

        ResetAll();
        Confirmbtn.setDisable(true);
        
    }

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        new Alert(Alert.AlertType.CONFIRMATION, "Do you Want to Close the Order").showAndWait();
        ResetAll();
    }

    private String getOrderId() throws ClassNotFoundException, SQLException {
        String sql="select oid from orders order by oid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }

    private void setOrderId() throws ClassNotFoundException, SQLException {
        
        String id=getOrderId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("O");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="O"+(x+1);
                    
                }else if(x>=9){
                    a="O0"+(x+1);
                    
                }else if(x>0){
                    a="O00"+(x+1);
                }
                id=a;
            } else {
                a="O001";
                id=a;
            }
            lblOrderid.setText(id);
    }

    @FXML
    private void disablecheckbox_OnAction(ActionEvent event) {
        if (disablecheckbox.isSelected()) {
            Discountpane1.setDisable(true);
            totalamount-=deliveryamount;
            deliveryamount=0.0;
            lbltotalamount.setText(String.valueOf(totalamount));
            lbldeliveryamount.setText(null);
            lbldeliveryamount.setText(null);
            lblcity.setText(null);
            lblcityid.setText(null);
            lblamount.setText(null);
            
        }else{
            Discountpane1.setDisable(false);
        }
    }

    private void Setcombobox() throws ClassNotFoundException, SQLException {
        String sql="select * from delivery";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
       ObservableList<String> oblist = FXCollections.observableArrayList();
        
        while (resultSet.next()) {            
            oblist.add(resultSet.getString(1));
        }
        citycombobox.setItems(oblist);
    }

    @FXML
    private void citycombobox_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        String sql="select * from delivery where city=?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, citycombobox.getValue());
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
       ObservableList<Delivery> oblist = FXCollections.observableArrayList();
        Delivery d = null;
            while (resultSet.next()) {                
                d=new Delivery(resultSet.getString("city"), resultSet.getString("deliid"), resultSet.getDouble("Amount"));
            }
            
            lblcity.setText(d.getCity());
            lblcityid.setText(d.getCityid());
            lblamount.setText(String.valueOf(d.getAmount()));
            
            deliveryamount=d.getAmount();
            totalamount+=deliveryamount;
            lbltotalamount.setText(String.valueOf(totalamount));
            lbldeliveryamount.setText(String.valueOf(deliveryamount));
    }

    private void AddOrder() throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO ORDERS (oid,cid,description) values (?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, lblOrderid.getText());
        prepareStatement.setString(2, lblId.getText());
        prepareStatement.setString(3, "");
        
        prepareStatement.execute();
    }

    private void AddOrderdetail() throws ClassNotFoundException, SQLException {
        Item i=new Item();
        for (Order item : itemtable.getItems()) {
            System.out.println(item.getQty());

        String first="SELECT quantityonhand FROM items WHERE iid=?";
        PreparedStatement p1 = connection.prepareStatement(first);
        p1.setString(1, item.getId());
        ResultSet executeQuery = p1.executeQuery();
            if (executeQuery.next()) {
                qty=item.getQty();
                qtyonhand=executeQuery.getInt("quantityonhand");
            }
        
        String second="UPDATE items SET quantityonhand=? where iid=?";
        PreparedStatement p2 = connection.prepareStatement(second);
        p2.setInt(1, qtyonhand-qty);
        p2.setString(2, item.getId());
        p2.execute();
        
        String sql="INSERT INTO orderdetail values (?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, lblOrderid.getText());
        prepareStatement.setString(2, item.getId());
        prepareStatement.setInt(3, item.getQty());
        prepareStatement.execute();
        }
    }

    private void makePayment() throws ClassNotFoundException, SQLException {
        
        String id=getpaymenid();
        String sql="INSERT INTO payment values (?,?,?,?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, id);
        prepareStatement.setString(2, lblOrderid.getText());
        prepareStatement.setString(3, lblcityid.getText());
        prepareStatement.setDouble(4, deliveryamount);
        prepareStatement.setDouble(5, itemsamount);
        prepareStatement.setDouble(6, totalamount);
        prepareStatement.setString(7, "");
        
        
        prepareStatement.execute();        
    }

    private String getpaymenid() throws ClassNotFoundException, SQLException {
                String id=getpId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("P");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="P"+(x+1);
                    
                }else if(x>=9){
                    a="P0"+(x+1);
                    
                }else if(x>0){
                    a="P00"+(x+1);
                }
                id=a;
                return id;
            } else {
                a="P001";
                id=a;
                return id;
            }
    }

    private String getpId() throws ClassNotFoundException, SQLException {
        String sql="select pid from payment order by pid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }

    private void ResetAll() throws ClassNotFoundException, SQLException {
        txtMob.setEditable(true);txtMob.setDisable(false);txtMob.clear();
        submitbtn.setDisable(false);
        showbtn.setDisable(false);
        MiddleAnchorpane.setDisable(true);
        Discountpane.setDisable(true);
        Confirmbtn.setDisable(true);
        Cancelbtn.setDisable(true);
        lblName.setText(null);lblId.setText(null);lblEmail.setText(null);lblAddress.setText(null);
        lblItemname.setText(null);lblItemdescription.setText(null);lblitemsamount.setText(null);lblunitprice.setText(null);
        price.setText(null);
        
        txtItemid.setText(null);
        txtItemid.setDisable(false);
        
        disablecheckbox.setSelected(true);
        lblcity.setText(null);lblcityid.setText(null);lblamount.setText(null);
        lbldeliveryamount.setText(null);lbltotalamount.setText(null);
        itemtable.getItems().clear();
        setOrderId();
    }
    
    
    
    
}
