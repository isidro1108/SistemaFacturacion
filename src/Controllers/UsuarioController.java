/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IUsuarioController;
import Entities.Usuario;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author euris
 */
public class UsuarioController implements IUsuarioController {
    private final DataContext _dbContext;
    
    public UsuarioController(DataContext dbContext) {
        _dbContext = dbContext;
    }

    @Override
    public List<Usuario> getAll() {
        Connection connection = _dbContext.connect();
        List<Usuario> usuarios = new ArrayList<>();
        
        String sql = "SELECT * FROM usuario";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setId(result.getInt("id"));
                usuario.setName(result.getString("name"));
                usuario.setLastName(result.getString("last_name"));
                usuario.setUsername(result.getString("username"));
                usuario.setEmail(result.getString("email"));
                usuario.setPassword(result.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuarios;
    }

    @Override
    public Usuario getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void create(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(int id, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
