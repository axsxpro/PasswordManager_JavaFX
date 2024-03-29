package org.example.ex_application_bureau.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO implements GenericDAO<User> {

    private StoredProcedure StoredProcedure;
    Map<Integer, User> arrayUser = new HashMap<>();


    public UserDAO() throws SQLException {

        this.StoredProcedure = new StoredProcedure();
    }


    public User create(User user) {

        return user;
    }


    @Override
    public User findById(int id) {

        return null;
    }


    @Override
    public User update(User user) {

        return user;

    }

    @Override
    public void delete(int id) {

    }

    public Map<Integer, User> findAll() throws SQLException {

        return arrayUser;
    }


}
