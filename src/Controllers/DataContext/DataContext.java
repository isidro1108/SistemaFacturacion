/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.DataContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author euris
 */
public class DataContext {
    private final String database = "sistema_facturacion";
    private final String user = "postgres";
    private final String password = "postgreSQL1108";
    private final String host = "localhost";
    private final String port = "5432";
    private final String driver = "org.postgresql.Driver";
    private final String connectionString = "jdbc:postgresql://" + host + ":" + port +"/" + database;
    
    public Connection connect() {
        Connection connection = null;
        
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(connectionString, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
}
