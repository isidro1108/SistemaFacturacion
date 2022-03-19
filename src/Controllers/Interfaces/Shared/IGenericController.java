/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controllers.Interfaces.Shared;

/**
 *
 * @author euris
 * @param <T>
 */
public interface IGenericController<T> extends IBaseGenericController<T> {
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
