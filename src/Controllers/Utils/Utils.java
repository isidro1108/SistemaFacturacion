/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.Utils;

import Controllers.UsuarioController;
import Entities.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author euris
 */
public class Utils {
    private static char echoChar;
    private static Usuario currentUser;
    
//    Private methods
    private static SecretKeySpec getSecretKeySpec() {
        String KEY = "SistemaFacturacion Java NetBeans";
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-1");
            byte[] passwordKey = md5.digest(KEY.getBytes("utf-8"));
            byte[] bytesKey = Arrays.copyOf(passwordKey, 16);
            return new SecretKeySpec(bytesKey, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            return null;
        }
    }
    
//    Public methods
    public static Usuario getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Usuario currentUser) {
        Utils.currentUser = currentUser;
    }
    
    public static boolean isFirstUser() {
        UsuarioController usuarioController = new UsuarioController();
        List<Usuario> usuarios = usuarioController.getAll();
        return usuarios.isEmpty();
    }
    
    public static void showAndHideText(JPasswordField jPasswordField, JCheckBox jCheckBox) {
        if (jPasswordField.getEchoChar() != (char)0) {
            echoChar = jPasswordField.getEchoChar();
        }
        jPasswordField.setEchoChar(jCheckBox.isSelected() ? (char)0 : echoChar);
    }
    
    public static String encode(String password) {
        String passwordEncrypted = "";
        
        try {
            SecretKey secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] passwordToBytes = password.getBytes("utf-8");
            byte[] buffer = cipher.doFinal(passwordToBytes);
            byte[] base64Bytes = Base64.encodeBase64(buffer);
            passwordEncrypted = new String(base64Bytes);            
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return passwordEncrypted;
    }
    
    public static String decode(String passwordEncrypted) {
        String passwordDesencrypted = "";
        
        try {           
            SecretKey secretKey = getSecretKeySpec();
            Cipher decipher = Cipher.getInstance("AES");
            decipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] message = Base64.decodeBase64(passwordEncrypted.getBytes("utf-8"));
            byte[] plainText = decipher.doFinal(message);
            passwordDesencrypted = new String(plainText);
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return passwordDesencrypted;
    }
}
