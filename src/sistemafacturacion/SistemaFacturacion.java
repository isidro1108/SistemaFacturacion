/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemafacturacion;

import Controllers.Utils.Utils;
import Views.Login;
import Views.MainMenu;
import Views.WelcomeUserAdmin;

/**
 *
 * @author euris
 */
public class SistemaFacturacion {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (Utils.isFirstUser()) {
            WelcomeUserAdmin welcomeView = new WelcomeUserAdmin();
            welcomeView.setVisible(true);
        } else {
//            Login login = new Login();
//            login.setVisible(true);
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        }
    }
    
}
