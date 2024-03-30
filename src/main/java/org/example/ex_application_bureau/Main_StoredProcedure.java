package org.example.ex_application_bureau;

import org.example.ex_application_bureau.Model.DAOFactory;
import org.example.ex_application_bureau.Model.User;

import java.sql.SQLException;
import java.util.Map;

public class Main_StoredProcedure {

    public static void main(String[] args) {

        try {

       Map<Integer, User> tableUser = DAOFactory.getUserDAO().findAll();

         for (User user : tableUser.values()) {

                System.out.println(user);
            }
        } catch (SQLException e) {
          e.printStackTrace();
       }


        }

}
