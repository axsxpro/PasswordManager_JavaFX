package org.example.ex_application_bureau.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO implements GenericDAO<User> {

    private StoredProcedure StoredProcedure;
    Map<Integer, User> arrayUser = new HashMap<>();


    public UserDAO(StoredProcedure storedProcedure) {

        this.StoredProcedure = storedProcedure;
    }


    public User create(User user) {

        return user;
    }


    @Override
    public User findById(int id) {

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureFindById("{call findOneUserById(?)}", id);

            while (resultStoredProcedure.next()) {

                int idUser = resultStoredProcedure.getInt("ID_USER");
                String email = resultStoredProcedure.getString("EMAIL");
                String password = resultStoredProcedure.getString("PASSWORD");

                User user = new User(idUser, email, password);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public User findUserByEmail(String email) {

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureFindByEmail("{call findUserByEmail(?)}", email);

            // Si un utilisateur correspondant à l'email est trouvé, retournez-le
            if (resultStoredProcedure.next()) {

                int idUser = resultStoredProcedure.getInt("ID_USER");
                String emailUser = resultStoredProcedure.getString("EMAIL");
                String password = resultStoredProcedure.getString("PASSWORD");


                return new User(idUser, emailUser, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun utilisateur trouvé avec cet email
    }


    @Override
    public User update(User user) {

        return user;

    }

    @Override
    public void delete(int id) {

    }

    public Map<Integer, User> findAll() throws SQLException {

        try {

            ResultSet resultStoredProcedure = StoredProcedure.procedureFindAll("{call findAllUser()}");

            while (resultStoredProcedure.next()) {

                int idUser = resultStoredProcedure.getInt("ID_USER");
                String email = resultStoredProcedure.getString("EMAIL");
                String password = resultStoredProcedure.getString("PASSWORD");

                User user = new User(idUser, email, password);

                arrayUser.put(idUser, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayUser;
    }


}
