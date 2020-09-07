/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.Hardware.DB.DBConnection;
import lk.Hardware.Model.Example;

/**
 * FXML Controller class
 *
 * @author dinusha
 */
public class HomePageController implements Initializable {

    @FXML
    private BarChart<String, Double> BarChart1;
    @FXML
    private LineChart<String, Integer> LineChart1;
    @FXML
    private BarChart<String, Double> BarChart2;
    @FXML
    private LineChart<String, Integer> LineChart2;
    @FXML
    private TableView<Example> Renttable;
    @FXML
    private TableColumn<Example, String> rid_column;
    @FXML
    private TableColumn<Example, String> cid_column;
    @FXML
    private TableColumn<Example, String> cname_column;
    @FXML
    private TableColumn<Example, String> rtid_column;
    @FXML
    private TableColumn<Example, Date> rtdate_column;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LoadBarChart();
            LoadLineChart();
            LoadTable();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void LoadBarChart() throws ClassNotFoundException, SQLException {
        Connection connection=DBConnection.getinstance().getConnection();
        String sql1="select i.iname,sum(od.qty) as qty\n" +
                   "from items i\n" +
                   "left join orderdetail od on i.iid=od.iid\n" +
                   "group by od.iid having qty>0;";
        
        String sql2="select ri.riname,sum(rd.qty) as qty\n" +
                   "from rentitems ri\n" +
                   "left join rentdetail rd on ri.riid=rd.riid\n" +
                   "group by rd.riid having qty>0;";        
        
        
        ResultSet rs1 = connection.createStatement().executeQuery(sql1);
        ResultSet rs2 = connection.createStatement().executeQuery(sql2);
        
        XYChart.Series<String,Double> series1=new XYChart.Series<>();
        XYChart.Series<String,Double> series2=new XYChart.Series<>();
        while (rs1.next()) {            
            series1.getData().add(new XYChart.Data<>(rs1.getString("iname"), rs1.getDouble("qty")));
        }
        while (rs2.next()) {            
            series2.getData().add(new XYChart.Data<>(rs2.getString("riname"), rs2.getDouble("qty")));
        }        
        BarChart1.getData().add(series1);
        BarChart2.getData().add(series2);
                
    }

    private void LoadLineChart() throws ClassNotFoundException, SQLException {
        Connection connection=DBConnection.getinstance().getConnection();
        String sql="select dayname(date) as day,count(month(date)) as orders from orders group by day";
        ResultSet rs = connection.createStatement().executeQuery(sql);
        XYChart.Series<String,Integer> series=new XYChart.Series<>();
        while (rs.next()) {            
            series.getData().add(new XYChart.Data<>(rs.getString("day"), rs.getInt("orders")));
        }
        LineChart1.getData().add(series);
        
        String sql1="select dayname(date) as day,count(month(date)) as rents from rent group by day";
        ResultSet rs1 = connection.createStatement().executeQuery(sql1);
        XYChart.Series<String,Integer> series1=new XYChart.Series<>();
        while (rs1.next()) {            
            series1.getData().add(new XYChart.Data<>(rs1.getString("day"), rs1.getInt("rents")));
        }
        LineChart2.getData().add(series1);        
    }

    private void LoadTable() {
        try {
            Connection connection = DBConnection.getinstance().getConnection();
            String sql="select r.rid,c.cid,c.cname,p.rtid,date(r.date) as date\n" +
                       "from rent r, customer c, renttaken p\n" +
                       "where c.cid=r.cid and r.rid=p.rid\n" +
                        "group by r.rid;";
            
            ResultSet rs=connection.createStatement().executeQuery(sql);
            ObservableList<Example> oblist = FXCollections.observableArrayList();
            while (rs.next()) {                
                oblist.add(new Example(rs.getString("rid"), rs.getString("cid"), rs.getString("cname"), rs.getString("rtid"), rs.getDate("date")));
            }
            
            
            rid_column.setCellValueFactory(new PropertyValueFactory<>("rid"));
            cid_column.setCellValueFactory(new PropertyValueFactory<>("cid"));
            cname_column.setCellValueFactory(new PropertyValueFactory<>("cname"));
            rtid_column.setCellValueFactory(new PropertyValueFactory<>("rtid"));
            rtdate_column.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            Renttable.setItems(oblist);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
