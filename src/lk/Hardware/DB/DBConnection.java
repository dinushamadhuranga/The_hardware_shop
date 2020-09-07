/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinusha
 */
public class DBConnection {
    private static DBConnection bConnection;
    
    private Connection connection;
    
    private DBConnection() throws ClassNotFoundException, SQLException{
        //Class.forName("com.mysql.cj.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hardware", "root", "root");
        
    }        

    public static DBConnection getinstance() throws ClassNotFoundException, SQLException{
        return bConnection==null ? bConnection=new DBConnection() : bConnection;
    }
    
    public Connection getConnection(){
        return connection;
    }
}
