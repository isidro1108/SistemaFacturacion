/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Utils;

import Controllers.UsuarioController;
import Entities.Usuario;
import java.util.List;

/**
 *
 * @author euris
 */
public class Utils {
    public static boolean isFirstUser() {
        UsuarioController usuarioController = new UsuarioController();
        List<Usuario> usuarios = usuarioController.getAll();
        return usuarios.isEmpty();
    }
}
