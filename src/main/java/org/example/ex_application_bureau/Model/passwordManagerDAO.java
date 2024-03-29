package org.example.ex_application_bureau.Model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class passwordManagerDAO implements GenericDAO<passwordManager> {

    private StoredProcedure StoredProcedure;
    private Map<Integer, passwordManager> arrayIdentifiant = new HashMap<>();


    public passwordManagerDAO() throws SQLException {

        this.StoredProcedure = new StoredProcedure();
    }


    public passwordManager create(passwordManager identifiant) {

        return identifiant;
    }


    @Override
    public passwordManager findById(int id) {

        return null;
    }


    @Override
    public passwordManager update(passwordManager identifiant) {

        return identifiant;

    }

    @Override
    public void delete(int id) {

    }

    public Map<Integer, passwordManager> findAll() throws SQLException {

        return arrayIdentifiant;
    }


}
