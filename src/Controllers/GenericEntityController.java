    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.Shared.IGenericController;
import Entities.GenericEntity;
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
public class GenericEntityController implements IGenericController<GenericEntity> {
    private final DataContext dbContext;
    private final String entityType;

    public GenericEntityController(String entityType) {
        this.dbContext = new DataContext();
        this.entityType = entityType;
    }
    
    @Override
    public List<GenericEntity> getAll() {
        Connection connection = dbContext.connect();
        List<GenericEntity> genericEntities = new ArrayList<>();
        String sql = "SELECT * FROM " + this.entityType + " ORDER BY id ASC";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                GenericEntity genericEntity = new GenericEntity();
                
                genericEntity.setId(result.getInt("id"));
                genericEntity.setName(result.getString("name"));
                genericEntities.add(genericEntity);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return genericEntities;
    }

    @Override
    public GenericEntity getById(int id) {
        Connection connection = dbContext.connect();
        GenericEntity entity = new GenericEntity();
        String sql = "SELECT * FROM " + this.entityType + " WHERE id=" + id;
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {
                entity.setId(result.getInt("id"));
                entity.setName(result.getString("name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return entity;
    }
    
    public GenericEntity getByName(String name) {
        Connection connection = dbContext.connect();
        GenericEntity entity = new GenericEntity();
        String sql = "SELECT * FROM " + this.entityType + " WHERE name='" + name + "'";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {
                entity.setId(result.getInt("id"));
                entity.setName(result.getString("name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return entity;
    }

    @Override
    public int create(GenericEntity entity) {
        Connection connection = dbContext.connect();
        int idEntity = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + this.entityType + "(name) "
                    + "VALUES ('" + entity.getName() + "') RETURNING id";
            
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) idEntity = result.getInt("id");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idEntity;
    }

    @Override
    public void update(GenericEntity entity) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE " + this.entityType + " SET "
                    + "name='" + entity.getName() + "'"
                    + "WHERE id=" + entity.getId();
            
            statement.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + this.entityType + "WHERE id =" + id;
            statement.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
