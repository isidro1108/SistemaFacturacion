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
     * @return 
     */
    int create(T entity);

    /**
     *
     * @param entity
     */
    void update(T entity);

    /**
     *
     * @param id
     */
    void delete(int id);
}
