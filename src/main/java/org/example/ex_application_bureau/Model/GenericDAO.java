package org.example.ex_application_bureau.Model;

import java.sql.SQLException;
import java.util.Map;


public interface GenericDAO<T> {

    public Map<Integer, T> findAll() throws SQLException;

    public T create(T obj);

    public  T findById(int id);

    public T update(T obj);

    public abstract void delete(int id);

}
