/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.Shared.IBaseGenericController;
import Entities.GenericEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author euris
 */
public class GenericEntityController implements IBaseGenericController<GenericEntity> {
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
            Logger.getLogger(GenericEntityController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GenericEntityController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GenericEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
}
