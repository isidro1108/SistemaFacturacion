/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers.Interfaces;

import Models.Usuario;
import java.util.List;

/**
 *
 * @author euris
 */
public interface IUsuarioController {

    /**
     *
     * @return
     */
    List<Usuario> getAll();

    /**
     *
     * @param id
     * @return
     */
    Usuario getById(int id);

    /**
     *
     * @param usuario
     */
    void create(Usuario usuario);

    /**
     *
     * @param id
     * @param usuario
     */
    void update(int id, Usuario usuario);

    /**
     *
     * @param id
     */
    void delete(int id);
}
