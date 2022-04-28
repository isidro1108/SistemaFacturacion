/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IEmpleadoController;
import Entities.Empleado;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author euris
 */
public class EmpleadoController implements IEmpleadoController {
    private final DataContext dbContext;
    
    public EmpleadoController() {
        this.dbContext = new DataContext();
    }
    
    @Override
    public List<Empleado> getAll() {
        Connection connection = dbContext.connect();
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY id ASC";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Empleado empleado = new Empleado();
                
                empleado.setId(result.getInt("id"));
                empleado.setName(result.getString("name"));
                empleado.setLastName(result.getString("last_name"));
                empleado.setAddress(result.getString("address"));
                empleado.setPhone(result.getString("phone"));
                empleado.setIdentityCard(result.getString("identity_card"));
                empleado.setEmail(result.getString("email"));
                empleado.setSalary(result.getFloat("salary"));
                empleado.setDateOfBirth(result.getDate("date_of_birth"));
                empleado.setDateOfAdmission(result.getDate("date_of_admission"));
                empleado.setSales(result.getFloat("sales"));
                empleados.add(empleado);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleados;
    }

    @Override
    public Empleado getById(int id) {
        Connection connection = dbContext.connect();
        Empleado empleado = new Empleado();
        String sql = "SELECT * FROM employee WHERE id=" + id;
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {                
                empleado.setId(result.getInt("id"));
                empleado.setName(result.getString("name"));
                empleado.setLastName(result.getString("last_name"));
                empleado.setAddress(result.getString("address"));
                empleado.setPhone(result.getString("phone"));
                empleado.setIdentityCard(result.getString("identity_card"));
                empleado.setEmail(result.getString("email"));
                empleado.setSalary(result.getFloat("salary"));
                empleado.setDateOfBirth(result.getDate("date_of_birth"));
                empleado.setDateOfAdmission(result.getDate("date_of_admission"));
                empleado.setSales(result.getFloat("sales"));
            } else {
                JOptionPane.showMessageDialog(null, "Este empleado no existe", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return empleado;
    }

    @Override
    public int create(Empleado empleado) {
        Connection connection = dbContext.connect();
        String sql = "INSERT INTO employee (name, last_name, address, phone, identity_card, email, salary, date_of_birth, date_of_admission, sales)"
                + "VALUES ('"+ empleado.getName() + "', "
                + "'" + empleado.getLastName() + "', "
                + "'" + empleado.getAddress() + "', "
                + "'" + empleado.getPhone() + "', "
                + "'" + empleado.getIdentityCard() + "', "
                + "'" + empleado.getEmail() + "', "
                + empleado.getSalary() + ", "
                + "'" + empleado.getDateOfBirth()+ "', "
                + "'" + empleado.getDateOfAdmission()+ "', "
                + "'" + empleado.getSales()+ "') RETURNING id";
        
        int idEmployee = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) idEmployee = result.getInt("id");
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idEmployee;
    }

    @Override
    public void update(Empleado empleado) {
        Connection connection = dbContext.connect();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE employee SET "
                    + "name='" + empleado.getName() + "', "
                    + "last_name='" + empleado.getLastName() + "', "
                    + "address='" + empleado.getAddress() + "', "
                    + "phone='" + empleado.getPhone() + "', "
                    + "identity_card='" + empleado.getIdentityCard()+ "', "
                    + "email='" + empleado.getEmail() + "', "
                    + "salary=" + empleado.getSalary() + ", "
                    + "date_of_birth='" + empleado.getDateOfBirth() + "', "
                    + "date_of_admission='" + empleado.getDateOfAdmission() + "', "
                    + "sales=" + empleado.getSales()
                    + " WHERE id=" + empleado.getId();
            
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
            String sql = "DELETE FROM employee WHERE id=" + id;
            statement.execute(sql);
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
