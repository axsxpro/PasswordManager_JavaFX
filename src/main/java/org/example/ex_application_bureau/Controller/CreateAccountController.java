package org.example.ex_application_bureau.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.ex_application_bureau.Main;
import org.example.ex_application_bureau.Model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountController {

    @FXML
    private Button button_reset;

    @FXML
    private Button create_Button;

    @FXML
    private TextField field_email;

    @FXML
    private PasswordField field_password;

    @FXML
    private Label label_error;

    @FXML
    private Label label_validation;

    private UserDAO userDAO;


    //constructeur
    public CreateAccountController() throws SQLException {

        this.userDAO = (UserDAO) DAOFactory.getUserDAO(); // cast qui indique que l'objet retourné par DAOFactory.getUserDAO() doit être traité comme un objet de type UserDAO.

    }


    //méthode pour ajouter un utilisateur (créer un compte Password Manager)
    public void addUser() {


        // Vérifier si l'e-mail est déjà utilisé
        if (userDAO.findUserByEmail(field_email.getText()) != null) {

            label_error.setText("Email already exists!");

            return; // Arrêter l'exécution de la méthode si l'e-mail existe déjà
        }


        if ((!field_email.getText().isBlank()) && !field_password.getText().isBlank()) {

            String password = field_password.getText();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); //hasher le mot de passe
            int idUser = 0; //mettre l'id à zéro car auto-incrémentation dans la bdd

            User newUser = new User(

                    idUser,
                    field_email.getText(),
                    hashedPassword
            );

            userDAO.create(newUser);

            System.out.println("User created !" + newUser);

            label_validation.setText("Account created !");


            // Après la création réussie de l'utilisateur, ouvrir la fenêtre de connexion
            try {

                Main main = new Main();
                main.openLoginWindow();

            } catch (IOException e) {

                e.printStackTrace();
            }

        } else {

            System.err.println("Error ! ");
            label_error.setText("Error when creating account");
        }

    }


    //méthode pour actionner le bouton 'create'
    @FXML
    private void createUser(ActionEvent event) {

        if ((field_email.getText().isBlank()) || field_password.getText().isBlank()) {

            label_error.setText("Field cannot be blank");

        } else if (!isValidEmail(field_email.getText())) {

            label_error.setText("Invalid email format");

        } else {

            addUser();

            //mettre les champs vides
            field_email.clear();
            field_password.clear();
            label_error.setText("");
        }
    }


    //contrainte, vérification du format de l'email
    private boolean isValidEmail(String email) {

        // Expression régulière pour valider le format de l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    //méthode pour actionner le bouton 'reset'
    @FXML
    private void resetForm(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        field_email.clear();
        field_password.clear();
        label_error.setText("");
        label_validation.setText("");

    }


}
