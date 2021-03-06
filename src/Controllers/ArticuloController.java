/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IArticuloController;
import Entities.Articulo;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author euris
 */
public class ArticuloController implements IArticuloController<Articulo> {
    private final DataContext dbContext;

    public ArticuloController() {
        this.dbContext = new DataContext();
    }
    
    @Override
    public int create(Articulo entity) {
        Connection connection = dbContext.connect();
        int idArticulo = 0;
        
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO item (id_item_type, code, name, description, quantity, purchase_price, sale_price, reorder_point, itbis)"
                    + "VALUES (" + entity.getIdItemType() + ", "
                    + "'"+ entity.getCode() + "', "
                    + "'"+ entity.getName() + "', "
                    + "'"+ entity.getDescription() + "', "
                    + entity.getQuantity() + ", "
                    + entity.getPurchasePrice() + ", "
                    + entity.getSalePrice() + ", "
                    + entity.getReorderPoint() + ", "
                    + entity.getItbis() + ") RETURNING id";
            
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) idArticulo = result.getInt("id");
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idArticulo;
    }

    @Override
    public void update(Articulo entity) {
        Connection connection = dbContext.connect();
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE item SET "
                    + "id_item_type=" + entity.getIdItemType() + ", "
                    + "code='" + entity.getCode() + "', "
                    + "name='" + entity.getName() + "', "
                    + "description='" + entity.getDescription() + "', "
                    + "quantity=" + entity.getQuantity() + ", "
                    + "purchase_price=" + entity.getPurchasePrice() + ", "
                    + "sale_price=" + entity.getSalePrice() + ", "
                    + "reorder_point=" + entity.getReorderPoint() + ", "
                    + "itbis=" + entity.getItbis()
                    + " WHERE id=" + entity.getId();

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
            String sql = "DELETE FROM item WHERE id=" + id;
            statement.execute(sql);
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Articulo> getAll() {
        Connection connection = dbContext.connect();
        List<Articulo> articulos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM item ORDER BY id ASC";
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Articulo articulo = new Articulo();
                
                articulo.setId(result.getInt("id"));
                articulo.setIdItemType(result.getInt("id_item_type"));
                articulo.setCode(result.getString("code"));
                articulo.setName(result.getString("name"));
                articulo.setDescription(result.getString("description"));
                articulo.setQuantity(result.getInt("quantity"));
                articulo.setPurchasePrice(result.getFloat("purchase_price"));
                articulo.setSalePrice(result.getFloat("sale_price"));
                articulo.setReorderPoint(result.getInt("reorder_point"));
                articulo.setItbis(result.getFloat("itbis"));
                
                if (articulo.getQuantity() > 0) articulos.add(articulo);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return articulos;
    }

    @Override
    public Articulo getById(int id) {
        Connection connection = dbContext.connect();
        Articulo articulo = new Articulo();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM item WHERE id=" + id;
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {
                articulo.setId(result.getInt("id"));
                articulo.setIdItemType(result.getInt("id_item_type"));
                articulo.setCode(result.getString("code"));
                articulo.setName(result.getString("name"));
                articulo.setDescription(result.getString("description"));
                articulo.setQuantity(result.getInt("quantity"));
                articulo.setPurchasePrice(result.getFloat("purchase_price"));
                articulo.setSalePrice(result.getFloat("sale_price"));
                articulo.setReorderPoint(result.getInt("reorder_point"));
                articulo.setItbis(result.getFloat("itbis"));
            } else {
                JOptionPane.showMessageDialog(null, "Este art??culo no existe", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return articulo;
    }
}
