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
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
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
            } else {
                JOptionPane.showMessageDialog(null, "Este usuario no existe", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return usuario;
    }

    @Override
    public int create(Usuario usuario) {
        Connection connection = dbContext.connect();
        String sql = "INSERT INTO \"user\" (id_role, id_gender, name, last_name, username, email, password)"
                + "VALUES ('"+ usuario.getIdRole() +"', "
                + "'"+ usuario.getIdGender() +"', "
                + "'"+ usuario.getName() +"', "
                + "'"+ usuario.getLastName() +"', "
                + "'"+ usuario.getUsername() +"', "
                + "'"+ usuario.getEmail() +"', "
                + "'"+ usuario.getPassword() +"') RETURNING id";
        int idUser = 0;
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if (result.next()) idUser = result.getInt("id");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idUser;
    }

    @Override
    public void update(Usuario usuario) {
        Connection connection = dbContext.connect();
        String passwordEncrypted = Utils.encode(usuario.getPassword());
        
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE \"user\" SET "
                    + "id_role=" + usuario.getIdRole() + ", "
                    + "id_gender=" + usuario.getIdGender() + ", "
                    + "name='" + usuario.getName() + "', "
                    + "last_name='" + usuario.getLastName() + "', "
                    + "username='" + usuario.getUsername() + "', "
                    + "email='" + usuario.getEmail() + "', "
                    + "password='" + passwordEncrypted + "' "
                    + "WHERE id=" + usuario.getId();
            
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
            String sql = "DELETE FROM \"user\" WHERE id=" + id;
            statement.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
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
