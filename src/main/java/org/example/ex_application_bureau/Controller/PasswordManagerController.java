package org.example.ex_application_bureau.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ex_application_bureau.Model.DAOFactory;
import org.example.ex_application_bureau.Model.PasswordManager;
import org.example.ex_application_bureau.Model.PasswordManagerDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;


public class PasswordManagerController {

    @FXML
    private Button delete_button;

    @FXML
    private Button modify_button;

    @FXML
    private PasswordField passwordField_pm;

    @FXML
    private Label passwordText_pm;

    @FXML
    private Label password_pm;

    @FXML
    private TextField usernameField_pm;

    @FXML
    private Label usernameText_pm;

    @FXML
    private Label username_pm;

    @FXML
    private Button cancel_button;

    @FXML
    private Button save_button;

    @FXML
    private Label label_pm;

    @FXML
    private Label urlText_pm;

    @FXML
    private Label emailText_pm;

    private PasswordManagerDAO passwordManagerDAO;

    private PasswordManager currentIdentifier;

    private ListPasswordController listPasswordController;


    public PasswordManagerController() throws SQLException {

        this.passwordManagerDAO = (PasswordManagerDAO) DAOFactory.getPasswordManagerDAO(); // cast qui indique que l'objet retourné par DAOFactory.getPasswordManagerDAO() doit être traité comme un objet de type PasswordManagerDAO.

    }


    // lorsque la fenetre PasswordManager sera ouverte on va initialiser les attributs et la methode collectId pour affichage des données
    public void initialize(PasswordManager identifiantSelected, ListPasswordController listPasswordControllerInstance) {

        currentIdentifier = identifiantSelected;

        listPasswordController = listPasswordControllerInstance;

        collectId(currentIdentifier.getUsername(), currentIdentifier.getPassword(), currentIdentifier.getUrl());

    }


    //methode qui permet de mettre à jour les données au niveau de la vue
    public void collectId(String username, String password, String url) {

        // Afficher la valeur dans le label
        usernameText_pm.setText(username);
        // Afficher la valeur dans le label
        passwordText_pm.setText(password);
        // Afficher la valeur dans le label
        urlText_pm.setText(url);


        // Affichage des fonctionnalités lors de l'ouverture de la fenètre password Manager
        usernameText_pm.setVisible(true);
        passwordText_pm.setVisible(true);


        // Cacher des fonctionnalités lors de l'ouvertuge de la fenètre password Manager
        usernameField_pm.setVisible(false);
        passwordField_pm.setVisible(false);
        save_button.setVisible(false);
        cancel_button.setVisible(false);

    }


    @FXML
    public void modifyData() {

        if (currentIdentifier == null) {

            System.out.println("No identifier to update");
            return;

        }

        if (usernameField_pm.getText().isBlank() && !passwordField_pm.getText().isBlank()) {

            String password = passwordField_pm.getText();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            PasswordManager updatedIdentifier = new PasswordManager(

                    currentIdentifier.getIdPasswordManager(),
                    currentIdentifier.getUsername(),
                    hashedPassword,
                    currentIdentifier.getUrl(),
                    currentIdentifier.getIdUser()
            );

            passwordManagerDAO.update(updatedIdentifier);

            collectId(usernameText_pm.getText(), hashedPassword, urlText_pm.getText());

            listPasswordController.updateTableView(updatedIdentifier);

        }
    }



    //methode qui actionne le button 'save' pour enregistrer les données
    @FXML
    public void saveData(ActionEvent event) {


        if (usernameField_pm.getText().isBlank() && passwordField_pm.getText().isBlank()) {

            label_pm.setText("Field cannot be blank, enter a value or cancel");

        } else {

            modifyData();


            // Remettre le texte du label d'alerte à une chaîne vide
            label_pm.setText("");

            // Lors du click sur le bouton save on active ou on désactive certaines fonctionnalités
            //false
            save_button.setVisible(false);
            cancel_button.setVisible(false);
            usernameField_pm.setVisible(false);
            passwordField_pm.setVisible(false);

            //true
            modify_button.setVisible(true);
            delete_button.setVisible(true);
            usernameText_pm.setVisible(true);
            passwordText_pm.setVisible(true);

        }

    }



    // bouton pour modifier les informations
    @FXML
    public void modifyId(ActionEvent event) {

        //mettre les champs vides
        usernameField_pm.clear();
        passwordField_pm.clear();

        // Afficher des fonctionnalités lors du click sur le bouton "modify"
        save_button.setVisible(true);
        cancel_button.setVisible(true);
        usernameField_pm.setVisible(true);
        passwordField_pm.setVisible(true);

        // Cacher des fonctionnalités lors du click sur le bouton "modify"
        usernameText_pm.setVisible(false);
        passwordText_pm.setVisible(false);
        modify_button.setVisible(false);
        delete_button.setVisible(false);

    }


    //methode qui actionne le bouton delete
    @FXML
    public void deleteId(ActionEvent event) {



    }




    @FXML
    public void cancelAction(ActionEvent event) {

        // Remettre le texte du label à une chaîne vide
        label_pm.setText("");

        // Renvoie des parametres dans la methode pour ré-affichage des données
        collectId(usernameText_pm.getText(), passwordText_pm.getText(), urlText_pm.getText());

        // Lors du cancel on active ou on désactive certaines fonctionnalités
        save_button.setVisible(false);
        cancel_button.setVisible(false);
        modify_button.setVisible(true);
        delete_button.setVisible(true);

    }


}