/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers.Interfaces.Shared;

import java.util.List;

/**
 *
 * @author euris
 * @param <T>
 */
public interface ICrudRepository<T> {
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

    /**
     *
     * @param entity
     */
    void create(T entity);

    /**
     *
     * @param id
     * @param entity
     */
    void update(int id, T entity);

    /**
     *
     * @param id
     */
    void delete(int id);
}
