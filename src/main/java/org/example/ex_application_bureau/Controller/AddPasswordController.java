package org.example.ex_application_bureau.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.ex_application_bureau.Model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class AddPasswordController {

    @FXML
    private Label label_error;

    @FXML
    private Label label_validation;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button reset_button;

    @FXML
    private Button save_button;

    @FXML
    private TextField url_field;

    @FXML
    private TextField username_field;

    private User currentUser;

    private ListPasswordController listPasswordController;

    private PasswordManagerDAO passwordManagerDAO;


    //constructeur
    public AddPasswordController() throws SQLException {

        this.passwordManagerDAO = (PasswordManagerDAO) DAOFactory.getPasswordManagerDAO();
    }


    public void initialize(User currentUser, ListPasswordController listPasswordControllerInstance) {

        this.currentUser = currentUser;
        this.listPasswordController = listPasswordControllerInstance;

    }


    public void addIdentifier() throws SQLException {

        String username = username_field.getText();
        String hashedPassword = BCrypt.hashpw(password_field.getText(), BCrypt.gensalt());
        String url = url_field.getText();


        //creation de l'objet identifiant
        //attention création de l'id avec 0 !
        PasswordManager newIdentifier = new PasswordManager(0, username, hashedPassword, url, currentUser);

        //creation du nouvel identifiant par la procédure stockée
        PasswordManager identifierCreated = passwordManagerDAO.create(newIdentifier);


        if (identifierCreated != null) {

            System.out.println("Identifier created ! -> " + identifierCreated);
            label_validation.setText("Identifier created !");
            resetForm();

            // Récupération du dernier identifiant généré automatiquement par la base de données
            int lastInsertedId = passwordManagerDAO.getLastId();

            //récupération de l'identifiant crée dans la bdd afin d'avoir le bon id
            PasswordManager identifierSelected = DAOFactory.getPasswordManagerDAO().findById(lastInsertedId);


            // Mise à jour du TableView dans le ListPasswordController avec le nouvel identifiant créé
            listPasswordController.collectIdForListPassword(


                    identifierSelected.getIdPasswordManager(),
                    identifierSelected.getUsername(),
                    identifierSelected.getPassword(),
                    identifierSelected.getUrl(),
                    identifierSelected.getIdUser()
            );


        } else {

            System.err.println("Error, identifier no created.");
            label_error.setText("Error, identifier no created.");
        }

    }


    @FXML
    private void saveNewPassword(ActionEvent event) throws SQLException {

        if ((password_field.getText().isBlank()) || username_field.getText().isBlank() || url_field.getText().isBlank()) {

            label_error.setText("Field cannot be blank");

        } else {

            addIdentifier();
        }

    }


    private void resetForm() {

        // Réinitialiser les champs du formulaire
        url_field.setText("https://"); //va remettre le champ pré-écrit
        username_field.clear();
        password_field.clear();
        label_error.setText("");

    }


    @FXML
    private void reset(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        url_field.setText("https://"); //va remettre le champ pré-écrit
        username_field.clear();
        password_field.clear();
        label_error.setText("");
        label_validation.setText("");

    }


}