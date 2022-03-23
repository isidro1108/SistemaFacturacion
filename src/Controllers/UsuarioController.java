/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.DataContext.DataContext;
import Controllers.Interfaces.IUsuarioController;
import Controllers.Utils.Utils;
import Entities.CustomResponses.AuthCustomResponse;
import Entities.Usuario;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
        Connection connection = dbContext.connect();
        String sql = "SELECT * FROM \"user\" WHERE id=" + id;
        Usuario usuario = new Usuario();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {
                usuario.setId(result.getInt("id"));
                usuario.setIdRole(result.getInt("id_role"));
                usuario.setIdGender(result.getInt("id_gender"));
                usuario.setName(result.getString("name"));
                usuario.setLastName(result.getString("last_name"));
                usuario.setUsername(result.getString("username"));
                usuario.setEmail(result.getString("email"));
                usuario.setPassword(result.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuario;
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
    
    public AuthCustomResponse auth(String username, String password) {
        Connection connection = dbContext.connect();        
        String sql = "SELECT * FROM \"user\" WHERE username='" + username + "'";
        
        AuthCustomResponse response = new AuthCustomResponse();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) {
                String passwordEncrypted = result.getString("password");
                String passwordDesencrypted = Utils.decode(passwordEncrypted);
                
                if (password.equals(passwordDesencrypted)) {
                    Usuario currentUser = this.getById(result.getInt("id"));
                    response.setCurrentUser(currentUser);
                    response.setIsAuth(true);
                } else {
                    response.setMessage("Contraseña incorrecta");
                    response.setTitle("Warning");
                    response.setMessageType(JOptionPane.WARNING_MESSAGE);
                }
            } else {
                response.setMessage("Este nombre de usuario no está registrado");
                response.setTitle("Warning");
                response.setMessageType(JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            response.setMessage("Ha ocurrido un error");
            response.setTitle("Error");
            response.setMessageType(JOptionPane.ERROR_MESSAGE);
        }
        
        return response;
    }
    
}
