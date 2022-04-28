/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IClienteController;
import Entities.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author euris
 */
public class ClienteController implements IClienteController {
    private final DataContext dbContext;

    public ClienteController() {
        this.dbContext = new DataContext();
    }
    
    @Override
    public List<Cliente> getAll() {
        Connection connection = dbContext.connect();
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY id ASC";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Cliente cliente = new Cliente();
                
                cliente.setId(result.getInt("id"));
                cliente.setName(result.getString("name"));
                cliente.setLastName(result.getString("last_name"));
                cliente.setAddress(result.getString("address"));
                cliente.setPhone(result.getString("phone"));
                cliente.setEmail(result.getString("email"));
                cliente.setDateOfBirth(result.getDate("date_of_birth"));
                cliente.setCreditLimit(result.getFloat("credit_limit"));
                clientes.add(cliente);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }

    @Override
    public Cliente getById(int id) {
        Connection connection = dbContext.connect();
        Cliente cliente = new Cliente();
        String sql = "SELECT * FROM customer WHERE id=" + id;
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {                
                cliente.setId(result.getInt("id"));
                cliente.setName(result.getString("name"));
                cliente.setLastName(result.getString("last_name"));
                cliente.setAddress(result.getString("address"));
                cliente.setPhone(result.getString("phone"));
                cliente.setEmail(result.getString("email"));
                cliente.setDateOfBirth(result.getDate("date_of_birth"));
                cliente.setCreditLimit(result.getFloat("credit_limit"));
            } else {
                JOptionPane.showMessageDialog(null, "Este cliente no existe", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return cliente;
    }

    @Override
    public int create(Cliente cliente) {
        Connection connection = dbContext.connect();
        String sql = "INSERT INTO customer (name, last_name, address, phone, email, date_of_birth, credit_limit)"
                + "VALUES ('"+ cliente.getName() +"', "
                + "'"+ cliente.getLastName()+"', "
                + "'"+ cliente.getAddress()+"', "
                + "'"+ cliente.getPhone() +"', "
                + "'"+ cliente.getEmail()+"', "
                + "'"+ cliente.getDateOfBirth()+"', "
                + "'"+ cliente.getCreditLimit()+"') RETURNING id";
        
        int idCustomer = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) idCustomer = result.getInt("id");
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idCustomer;
    }

    @Override
    public void update(Cliente cliente) {
        Connection connection = dbContext.connect();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE customer SET "
                    + "name='" + cliente.getName() + "', "
                    + "last_name='" + cliente.getLastName()+ "', "
                    + "address='" + cliente.getAddress()+ "', "
                    + "phone='" + cliente.getPhone() + "', "
                    + "email='" + cliente.getEmail()+ "', "
                    + "date_of_birth='" + cliente.getDateOfBirth()+ "', "
                    + "credit_limit=" + cliente.getCreditLimit()
                    + " WHERE id=" + cliente.getId();
            
            statement.execute(sql);
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM customer WHERE id=" + id;
            statement.execute(sql);
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
