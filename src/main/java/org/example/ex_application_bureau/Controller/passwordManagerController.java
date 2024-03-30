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
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.example.ex_application_bureau.Controller.ListPasswordController;


public class passwordManagerController {

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

    private ListPasswordController listPasswordController;





    //methode qui permet d'ouvrir la fenetre pour éditer l'email ou le mot de passe
    @FXML
    public void openPasswordManager(String username, String hashedPassword, String url, String email) {
        try {

            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader passwordManagerFXML = new FXMLLoader(getClass().getResource("passwordManager.fxml"));
            //loader.load() charge le fichier FXML
            Parent root = passwordManagerFXML.load();

            // Récupérer le contrôleur de la deuxième fenêtre
            passwordManagerController controller = passwordManagerFXML.getController();

            // Afficher les valeurs dans la deuxième fenêtre, apelle de la methode "collectId" dans le controller "passwordManagerFXML"
            controller.collectId(username, hashedPassword, url, email);

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("Password Manager");

            // Affichage de la fenêtre principale
            primaryStage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }



    public void collectId(String username, String password, String url, String email) {

        // Afficher la valeur dans le label
        usernameText_pm.setText(username);
        // Afficher la valeur dans le label
        passwordText_pm.setText(password);
        // Afficher la valeur dans le label
        urlText_pm.setText(url);
        // Afficher la valeur dans le label
        emailText_pm.setText(email);


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


    //methode qui actionne le button 'save'
    @FXML
    public void saveData(ActionEvent event) {


        if (usernameField_pm.getText().isBlank() && passwordField_pm.getText().isBlank()) {

            label_pm.setText("Field cannot be blank, enter a value or cancel");

        } else {


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


    @FXML
    public void cancelAction(ActionEvent event) {

        // Remettre le texte du label à une chaîne vide
        label_pm.setText("");

        // Renvoie des parametres dans la methode pour ré-affichage des données
        collectId(usernameText_pm.getText(), passwordText_pm.getText(), urlText_pm.getText(), emailText_pm.getText());

        // Lors du cancel on active ou on désactive certaines fonctionnalités
        save_button.setVisible(false);
        cancel_button.setVisible(false);
        modify_button.setVisible(true);
        delete_button.setVisible(true);

    }


}