package org.example.ex_application_bureau.Model.dao;

import org.example.ex_application_bureau.Model.DAOFactory;
import org.example.ex_application_bureau.Model.GenericDAO;
import org.example.ex_application_bureau.Model.StoredProcedure;
import org.example.ex_application_bureau.Model.entity.PasswordManager;
import org.example.ex_application_bureau.Model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PasswordManagerDAO implements GenericDAO<PasswordManager> {

    private org.example.ex_application_bureau.Model.StoredProcedure StoredProcedure;
    private Map<Integer, PasswordManager> arrayIdentifiant = new HashMap<>();

    private Map<Integer, PasswordManager> arrayIdentifiantByidUser = new HashMap<>();


    public PasswordManagerDAO(StoredProcedure storedProcedure) {

        this.StoredProcedure = storedProcedure;
    }


    public PasswordManager create(PasswordManager identifiant) {

        try {

            // Récupérer l'objet dans la table User correspondant à l'identifiant
            User user = DAOFactory.getUserDAO().findById(identifiant.getIdUser().getIdUser());

            StoredProcedure.procedureCreatePM("{call createPM(?, ?, ?, ?, ?)}",

                    identifiant.getIdPasswordManager(),
                    identifiant.getUsername(),
                    identifiant.getPassword(),
                    identifiant.getUrl(),
                    user

            );

            return identifiant;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public PasswordManager findById(int id) {

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureFindById("{call findOnePMById(?)}", id);

            if (resultStoredProcedure.next()) {

                int idPM = resultStoredProcedure.getInt("ID_passwordManager");
                String username = resultStoredProcedure.getString("USERNAME");
                String password = resultStoredProcedure.getString("PASSWORD");
                String url = resultStoredProcedure.getString("URL");
                int userId = resultStoredProcedure.getInt("ID_USER");

                User idUser = DAOFactory.getUserDAO().findById(userId);

                PasswordManager passwordManager = new PasswordManager(idPM, username, password, url, idUser);
                return passwordManager;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public PasswordManager update(PasswordManager identifiant) {

        try {

            StoredProcedure.procedureUpdatePM("{call updatePM(?, ?, ?)}",

                    identifiant.getIdPasswordManager(),
                    identifiant.getUsername(),
                    identifiant.getPassword()

            );

            return identifiant;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(int id) {

        try {

            StoredProcedure.procedureDeleteById("{call deletePMById(?)}", id);

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }


    public Map<Integer, PasswordManager> findAll() throws SQLException {

        return arrayIdentifiant;
    }


    public Map<Integer, PasswordManager> findByIdUser(int idUser) throws SQLException {

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureFindByIdUser("{call findAllPMbyIdUser(?)}", idUser);

            while (resultStoredProcedure.next()) {

                int idPM = resultStoredProcedure.getInt("ID_passwordManager");
                String username = resultStoredProcedure.getString("USERNAME");
                String password = resultStoredProcedure.getString("PASSWORD");
                String url = resultStoredProcedure.getString("URL");
                int userId = resultStoredProcedure.getInt("ID_USER");

                User user = DAOFactory.getUserDAO().findById(userId);

                PasswordManager identifiant = new PasswordManager(idPM, username, password, url, user);

                arrayIdentifiantByidUser.put(idPM, identifiant);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayIdentifiantByidUser;
    }


    public Integer getLastId() throws SQLException {

        int lastId = 0;

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureGetLastId("{call selectMaxIdPM}");

            while (resultStoredProcedure.next()) {

                lastId = resultStoredProcedure.getInt(1);
            }

            return lastId;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
