/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Fabricio
 */
public class DatabaseConnection {
    
    public DatabaseConnection() {
        
    }
    
     public static Connection openConnection() {
        Connection connection = null;
        
        String driverName = "com.mysql.jdbc.Driver"; 
        String serverName = "localhost"; 
        String portNumber = "3306";  
        String database = "animal_scores"; 
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + database;
        String username = "root"; 
        String password = "foreign"; 
        
        try {
            Class.forName(driverName);
            connection = (Connection) DriverManager.getConnection(url, username, password);
            
            if (connection == null) {
                System.out.println("The connection could not be stablished. Connection == NULL");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error while connection to the database: " + ex.getMessage());
        }
        return connection;
    }
    
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error while closing the database connection: " + ex.getMessage());
        }
    }
}
