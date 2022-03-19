/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers.Interfaces.Shared;

import java.util.List;

/**
 *
 * @author euris
 */
public interface IBaseGenericController<T> {
    /**
     *
     * @return
     */
    List<T> getAll();

    /**
     *
     * @param id
     * @return
     */
    T getById(int id);
}
