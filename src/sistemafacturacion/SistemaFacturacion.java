/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemafacturacion;

import Controllers.UsuarioController;
import Entities.Usuario;
import Views.Login;
import Views.Registro;
import java.util.List;

/**
 *
 * @author euris
 */
public class SistemaFacturacion {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();
        List<Usuario> usuarios = usuarioController.getAll();
        
        if (usuarios.isEmpty()) {
            Registro registro = new Registro();
            registro.setVisible(true);
        } else {
            Login login = new Login();
            login.setVisible(true);
        }
    }
    
}
