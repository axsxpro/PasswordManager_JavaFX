package org.example.ex_application_bureau.Model;

import org.example.ex_application_bureau.Util.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoredProcedure {

    private final Connection connection;

    // Constructeur
    public StoredProcedure(Connection connection) {

        this.connection = connection;
    }


    // methode procédure stocké pour rechercher toute la liste d'une table
    public ResultSet procedureFindAll(String callProcedureSQL) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);

        return callableStatement.executeQuery();
    }


    //rechercher tous les identifiants d'un user selon son idUser
    public ResultSet procedureFindByIdUser(String callProcedureSQL, int idUser) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idUser);

        return callableStatement.executeQuery();
    }

    //rechercher une donnée par son ID
    public ResultSet procedureFindById(String callProcedureSQL, int idType) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idType);

        return callableStatement.executeQuery();
    }


    // rechercher un utilisateur par son email
    public ResultSet procedureFindByEmail(String callProcedureSQL, String email) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setString(1, email);

        return callableStatement.executeQuery();
    }

    // mettre à jour un identifiant dans le password Manager
    public int procedureUpdatePM(String callProcedureSQL, int idPM, String username, String password) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idPM);
        callableStatement.setString(2, username);
        callableStatement.setString(3, password);

        return callableStatement.executeUpdate();
    }


    //methode pour supprimer une donnée par son id
    public ResultSet procedureDeleteById(String callProcedureSQL, int id) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, id);

        return callableStatement.executeQuery();
    }


    //créer un utilisateur
    public ResultSet procedureCreateUser(String callProcedureSQL, int idUser, String email, String password) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idUser);
        callableStatement.setString(2, email);
        callableStatement.setString(3, password);

        return callableStatement.executeQuery();

    }



}
