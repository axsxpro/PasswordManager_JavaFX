package org.example.ex_application_bureau.Model;

import org.example.ex_application_bureau.Util.Database;

import java.sql.SQLException;

public class DAOFactory {


    private static StoredProcedure storedProcedure;

    static {
        try {
            storedProcedure = new StoredProcedure(Database.connectionToDB());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static GenericDAO<User> getUserDAO() throws SQLException {

        return new UserDAO(storedProcedure);

    }

    public static GenericDAO<PasswordManager> getPasswordManagerDAO() throws SQLException {

        return new passwordManagerDAO(storedProcedure);

    }

}
