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
    private final DataContext dbContext;

    public UsuarioController() {
        this.dbContext = new DataContext();
    }

    @Override
    public List<Usuario> getAll() {
        Connection connection = dbContext.connect();
        List<Usuario> usuarios = new ArrayList<>();
        
        String sql = "SELECT * FROM \"user\" ORDER BY id ASC";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {
                Usuario usuario = new Usuario();
                
                usuario.setId(result.getInt("id"));
                usuario.setIdRole(result.getInt("id_role"));
                usuario.setIdGender(result.getInt("id_gender"));
                usuario.setName(result.getString("name"));
                usuario.setLastName(result.getString("last_name"));
                usuario.setUsername(result.getString("username"));
                usuario.setEmail(result.getString("email"));
                usuario.setPassword(result.getString("password"));
                
                usuarios.add(usuario);
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
        Connection connection = dbContext.connect();
        String sql = "INSERT INTO \"user\" (id_role, id_gender, name, last_name, username, email, password)"
                + "VALUES ('"+ usuario.getIdRole() +"', "
                + "'"+ usuario.getIdGender() +"', "
                + "'"+ usuario.getName() +"', "
                + "'"+ usuario.getLastName() +"', "
                + "'"+ usuario.getUsername() +"', "
                + "'"+ usuario.getEmail() +"', "
                + "'"+ usuario.getPassword() +"')";
        
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
