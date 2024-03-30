package org.example.ex_application_bureau.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class passwordManagerDAO implements GenericDAO<PasswordManager> {

    private StoredProcedure StoredProcedure;
    private Map<Integer, PasswordManager> arrayIdentifiant = new HashMap<>();

    private Map<Integer, PasswordManager> arrayIdentifiantByidUser = new HashMap<>();


    public passwordManagerDAO(StoredProcedure storedProcedure) {

        this.StoredProcedure = storedProcedure;
    }


    public PasswordManager create(PasswordManager identifiant) {

        return identifiant;
    }


    @Override
    public PasswordManager findById(int id) {

        return null;
    }


    @Override
    public PasswordManager update(PasswordManager identifiant) {

        return identifiant;

    }

    @Override
    public void delete(int id) {

    }


    public Map<Integer, PasswordManager> findAll() throws SQLException {

        return arrayIdentifiant;
    }


//    public Map<Integer, PasswordManager> findByIdUser(User idUser) throws SQLException {
//
//        try {
//
//            ResultSet resultStoredProcedure = StoredProcedure.procedureFindByIdUser("{call findAllPMbyIdUser()}", idUser);
//
//            while (resultStoredProcedure.next()) {
//
//                int idPM = resultStoredProcedure.getInt("ID_passwordManager");
//                String username = resultStoredProcedure.getString("USERNAME");
//                String password = resultStoredProcedure.getString("PASSWORD");
//                String url = resultStoredProcedure.getString("URL");
//                int userId = resultStoredProcedure.getInt("ID_USER");
//
//                User user = DAOFactory.getUserDAO().findById(userId);
//
//                PasswordManager identifiant = new PasswordManager(idPM, username, password, url, user);
//
//                arrayIdentifiantByidUser.put(idPM, identifiant);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return arrayIdentifiantByidUser;
//    }

//
//    }

}
