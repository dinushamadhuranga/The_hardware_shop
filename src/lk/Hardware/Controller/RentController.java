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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Item;
import lk.Hardware.Model.Order;
import lk.Hardware.Model.RentItem;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class RentController implements Initializable {

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
    private TableColumn<Order, String> idcolumn;
    @FXML
    private TableColumn<Order, String> namecolumn;
    @FXML
    private TableColumn<Order, Integer> qtycolumn;
    @FXML
    private TableColumn<Order, Double> pricecolumn;
    @FXML
    private JFXButton Addbtn;
    @FXML
    private JFXButton showbtn;
    @FXML
    private JFXButton finalizebtn;
    @FXML
    private JFXButton submitbtn;
    @FXML
    private Label lblRentid;
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
    private JFXButton Confirmbtn;
    @FXML
    private JFXButton Cancelbtn;
    private Label lbldeliveryamount;
    @FXML
    private Label lbltotalamount;
    private Label lblitemsamount;
    
    private Connection connection;
    private int qty;
    private double itemsamount;
    @FXML
    private Label lbldate;
    private double totalamount;
    private int qtyonhand;
    @FXML
    private AnchorPane ReloadPane;
    @FXML
    private JFXButton Paybtn;
    private JFXButton Closebtn;
    @FXML
    private Label lblrdate;
    @FXML
    private Label lblrtotalamount;
    @FXML
    private Label lblrdays;
    @FXML
    private JFXTextField txtBill;
    @FXML
    private JFXButton Reloadbtn;
    private int days;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection=DBConnection.getinstance().getConnection();
            ResetAll();        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }    

    @FXML
    private void Addbtn_OAction(ActionEvent event) throws SQLException {
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

            String sql="select * from rentitems where riid=\""+txtItemid.getText()+"\"";
 
            ObservableList<Order> oblist = FXCollections.observableArrayList();
            ResultSet rs=connection.createStatement().executeQuery(sql);
            Item i = null;
            while (rs.next()) {                
                i=new Item(rs.getString("riid"), rs.getString("riname"), rs.getDouble("unitperdayprice"), rs.getInt("quantityonhand"), rs.getString("description"));
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
        
                String sql="select * from rentitems where riid=?";
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtItemid.getText());
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {
                    qty=rs.getInt("quantityonhand");
                    lblItemname.setText(rs.getString("riname"));
                    lblItemdescription.setText(rs.getString("description"));
                    lblunitprice.setText(rs.getString("unitperdayprice"));
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
        //lblitemsamount.setText(String.valueOf(itemsamount));
        
        SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formDate.format(new Date());
        lbldate.setText(strDate);
        
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
        ReloadPane.setDisable(true);
                 
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
    private void Confirmbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        AddOrder();
        AddOrderdetail();
        makePayment();
        
        new Alert(Alert.AlertType.INFORMATION, "Rent Successfully added").showAndWait();

        ResetAll();
        Confirmbtn.setDisable(true);        
    }
    
    private void AddOrder() throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO rent (rid,cid,description) values (?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, lblRentid.getText());
        prepareStatement.setString(2, lblId.getText());
        prepareStatement.setString(3, "");
        
        prepareStatement.execute();
    }

    private void AddOrderdetail() throws ClassNotFoundException, SQLException {
        Item i=new Item();
        for (Order item : itemtable.getItems()) {
            System.out.println(item.getQty());

        String first="SELECT quantityonhand FROM rentitems WHERE riid=?";
        PreparedStatement p1 = connection.prepareStatement(first);
        p1.setString(1, item.getId());
        ResultSet executeQuery = p1.executeQuery();
            if (executeQuery.next()) {
                qty=item.getQty();
                qtyonhand=executeQuery.getInt("quantityonhand");
            }
        
        String second="UPDATE rentitems SET quantityonhand=? where riid=?";
        PreparedStatement p2 = connection.prepareStatement(second);
        p2.setInt(1, qtyonhand-qty);
        p2.setString(2, item.getId());
        p2.execute();
        
        String sql="INSERT INTO rentdetail values (?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, lblRentid.getText());
        prepareStatement.setString(2, item.getId());
        prepareStatement.setInt(3, item.getQty());
        prepareStatement.execute();
        }
    }

    private void makePayment() throws ClassNotFoundException, SQLException {
        
        String id=getRenttakenid();
        String sql="INSERT INTO renttaken (rtID,rId,PerDayTotalamount,description) VALUES (?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, id);
        prepareStatement.setString(2, lblRentid.getText());
        prepareStatement.setDouble(3, totalamount);
        prepareStatement.setString(4, "");
        
        prepareStatement.execute();        
    }    
    
    private String getRenttakenid() throws ClassNotFoundException, SQLException {
                String id=getrtId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("RT");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="RT"+(x+1);
                    
                }else if(x>=9){
                    a="RT0"+(x+1);
                    
                }else if(x>0){
                    a="RT00"+(x+1);
                }
                id=a;
                return id;
            } else {
                a="RT001";
                id=a;
                return id;
            }
    }  
    
    private String getrtId() throws ClassNotFoundException, SQLException {
        String sql="select rtid from renttaken order by rtid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }    

    @FXML
    private void Cancelbtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        new Alert(Alert.AlertType.CONFIRMATION, "Do you Want to Close the Order").showAndWait();
        ResetAll();        
    }
    
    
    private void ResetAll() throws ClassNotFoundException, SQLException {
        txtMob.setEditable(true);txtMob.setDisable(false);txtMob.clear();
        submitbtn.setDisable(false);
        showbtn.setDisable(false);
        MiddleAnchorpane.setDisable(true);
        ReloadPane.setDisable(false);
        Paybtn.setDisable(true);
        txtBill.setText(null);
        Reloadbtn.setDisable(false);
        txtBill.setEditable(true);
        
        Confirmbtn.setDisable(true);
        Cancelbtn.setDisable(true);
        lblName.setText(null);lblId.setText(null);lblEmail.setText(null);lblAddress.setText(null);
        lblItemname.setText(null);lblItemdescription.setText(null);lblunitprice.setText(null);
        price.setText(null);
        
        txtItemid.setText(null);
        txtItemid.setDisable(false);
        lblrdate.setText(null);
        lblrdays.setText(null);
        lblrtotalamount.setText(null);
        
        
        
        lbldate.setText(null);lbltotalamount.setText(null);
        itemtable.getItems().clear();
        setRentId();
    }    
    
    private void setRentId() throws ClassNotFoundException, SQLException {
        
        String id=getRentId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("R");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="R"+(x+1);
                    
                }else if(x>=9){
                    a="R0"+(x+1);
                    
                }else if(x>0){
                    a="R00"+(x+1);
                }
                id=a;
            } else {
                a="R001";
                id=a;
            }
            lblRentid.setText(id);
    }    
    
    private String getRentId() throws ClassNotFoundException, SQLException {
        String sql="select rid from rent order by rid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }    

    @FXML
    private void Paybtn_OnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        String rgid=getRentGivenid();
        String sql="INSERT INTO rentgiven (rgID,rId,totalamount,description) VALUES (?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, rgid);
        prepareStatement.setString(2, lblRentid.getText());
        prepareStatement.setDouble(3, Double.parseDouble(lblrtotalamount.getText()));
        prepareStatement.setString(4, null);
        prepareStatement.execute();
        new Alert(Alert.AlertType.INFORMATION,"The rent has been succefully payed").showAndWait();
        
        addRentItems();
        
        String sql1="DELETE FROM renttaken WHERE rtid=?";
        PreparedStatement prepareStatement1 = connection.prepareStatement(sql1);
        prepareStatement1.setString(1, txtBill.getText());
        prepareStatement1.execute();
        
        ResetAll();
    }
    
    private void addRentItems() throws SQLException {
        //RentItem i=new RentItem();
        for (Order item : itemtable.getItems()) {
            System.out.println(item.getQty());

            String first="SELECT quantityonhand FROM rentitems WHERE riid=?";
            PreparedStatement p1 = connection.prepareStatement(first);
            p1.setString(1, item.getId());
            ResultSet executeQuery = p1.executeQuery();
            if (executeQuery.next()) {
                qty=item.getQty();
                qtyonhand=executeQuery.getInt("quantityonhand");
            }
        
            String second="UPDATE rentitems SET quantityonhand=? where riid=?";
            PreparedStatement p2 = connection.prepareStatement(second);
            p2.setInt(1, qtyonhand+qty);
            p2.setString(2, item.getId());
            p2.execute();
        
        }        
    }
    
    private String getRentGivenid() throws ClassNotFoundException, SQLException {
                String id=getrgId();
        
        
        String a = null;
            if (id!=null) {
                System.out.println(id);
                String []parts=id.split("RG");
                String part1=parts[1];
                int x=Integer.parseInt(part1);
                if (x>=99) {
                    a="RG"+(x+1);
                    
                }else if(x>=9){
                    a="RG0"+(x+1);
                    
                }else if(x>0){
                    a="RG00"+(x+1);
                }
                id=a;
                return id;
            } else {
                a="RG001";
                id=a;
                return id;
            }
    }  
    
    private String getrgId() throws ClassNotFoundException, SQLException {
        String sql="select rgid from rentgiven order by rgid desc limit 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        
        ResultSet resultSet=prepareStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }     


    @FXML
    private void Reloadbtn_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (isEmptyr()) {
            new Alert(Alert.AlertType.ERROR, "please enter item id").showAndWait();
            return;
        } else {
            if (isValidr()) {
                new Alert(Alert.AlertType.ERROR, "please enter a exiting item's id").showAndWait();
                return;
            } 
        }        
    }
    private boolean isEmptyr() {
        return (txtBill.getText()==null);
    }    

    private boolean isValidr() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql="select * from renttaken where rtid=?";
                
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                prepareStatement.setString(1, txtBill.getText());        
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    //lblName.setText(rs.getString("iname"));
                    lblRentid.setText(rs.getString("rid"));
                    lbldate.setText(String.valueOf(rs.getDate("takendate")));
                    lbltotalamount.setText(String.valueOf(rs.getDouble("perdaytotalamount")));
                    SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = formDate.format(new Date());
                    lblrdate.setText(strDate);                    
                    
                    txtBill.setEditable(false);
                    
                    setCustomerid();
                    setDays();
                    
                } else {
                      return true;
        }
        return false;        
    }

    private void setCustomerid() throws SQLException, ClassNotFoundException {
                Connection connection = DBConnection.getinstance().getConnection();
                String sql1="select c.cid,c.cname,c.ctell,c.cemail,c.caddress\n" +
                            "from rent r\n" +
                            "left join customer c on r.cid=c.cid\n" +
                            "where r.rid=?\n" +
                            "order by c.cname;";
                PreparedStatement prepareStatement = connection.prepareStatement(sql1);
                prepareStatement.setString(1, lblRentid.getText());        
                
                ResultSet rs=prepareStatement.executeQuery();
                
                if (rs.next()) {

                    lblId.setText(rs.getString("cid"));
                    lblName.setText(rs.getString("cname"));
                    txtMob.setText(rs.getString("ctell"));
                    lblEmail.setText(rs.getString("cemail"));
                    lblAddress.setText(rs.getString("caddress"));
                }        
                
                
                LoadTable();
                
                Cancelbtn.setDisable(false);
                submitbtn.setDisable(true);
                txtMob.setEditable(false);
                Paybtn.setDisable(false);
                Reloadbtn.setDisable(true);
                        
    }

    private void LoadTable() throws SQLException {
        
        String sql="SELECT r.riid,r.riname,rd.qty,r.unitperdayprice*rd.qty as price\n" +
                    "FROM rentitems r\n" +
                    "left join rentdetail rd on r.riid=rd.riid\n" +
                    "left join rent re on rd.rid=re.rid\n" +
                    "where re.rid=?\n" +
                    "order by r.riname;";
        
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, lblRentid.getText());
        ResultSet rs = prepareStatement.executeQuery();
        
        ObservableList<Order> oblist = FXCollections.observableArrayList();
        
        while (rs.next()) {
            oblist.add(new Order(rs.getString("riid"), rs.getString("riname"), rs.getInt("qty"), rs.getDouble("price")));
        }
        
                idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                qtycolumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
                pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        itemtable.setItems(oblist);
    }

    private void setDays() throws SQLException {
        
        String sql="SELECT DATEDIFF(NOW(),takendate) as days FROM renttaken where rtid=?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, txtBill.getText());
        ResultSet executeQuery = prepareStatement.executeQuery();
        if (executeQuery.next()) {
            days=executeQuery.getInt("days");
        }
        
        if (days==0) {
            days=1;
        }
        lblrdays.setText(String.valueOf(days));
        
        totalamount=Double.parseDouble(lbltotalamount.getText())*days;
        lblrtotalamount.setText(String.valueOf(totalamount));
    }


}
