package org.example.ex_application_bureau.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {


    private static final String URL_DATABASE = "jdbc:mysql://localhost:3306/passwordmanager";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";


    public static Connection connectionToDB() throws SQLException {

        return DriverManager.getConnection(URL_DATABASE, USERNAME, PASSWORD);
    }


    public static void main(String[] args) {

        try (Connection connection = connectionToDB()) {

            if (connection != null) {

                System.out.println("Connected to the database!");

            } else {
                System.out.println("Failed to make connection!");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
