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


    public ResultSet procedureFindByIdUser(String callProcedureSQL, int idUser) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idUser);

        return callableStatement.executeQuery();
    }

    public ResultSet procedureFindById(String callProcedureSQL, int idType) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setInt(1, idType);

        return callableStatement.executeQuery();
    }


    public ResultSet procedureFindByEmail(String callProcedureSQL, String email) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(callProcedureSQL);
        callableStatement.setString(1, email);

        return callableStatement.executeQuery();
    }




}
