/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers.Interfaces;

import Entities.Usuario;
import Controllers.Interfaces.Shared.IGenericController;
import Entities.CustomResponses.AuthCustomResponse;

/**
 *
 * @author euris
 */
public interface IUsuarioController extends IGenericController<Usuario> {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    AuthCustomResponse auth(String username, String password);
}
