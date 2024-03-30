package org.example.ex_application_bureau.Controller;

import org.example.ex_application_bureau.Model.DAOFactory;
import org.example.ex_application_bureau.Model.PasswordManager;
import org.example.ex_application_bureau.Model.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import org.example.ex_application_bureau.Model.User;
import org.json.JSONArray;
import org.json.JSONObject;


public class LoginController {


    @FXML
    private Button login_button;

    @FXML
    private Label password;

    @FXML
    private TextField password_text;

    @FXML
    private Label username;

    @FXML
    private TextField username_text;

    @FXML
    private Label label_login;

    @FXML
    private Button reset_button;

    @FXML
    private TextField url_text;

    @FXML
    private TextField email_text;

    private List<Map<String, String>> arrayUsers = new ArrayList<>();

    private ListPasswordController listPasswordController; //  instance de la classe ListPasswordController, utilisée pour stocker le contrôleur de la fenêtre ListPassword

    private UserDAO userDAO;
    private PasswordManager passwordManagerDAO;

    public LoginController() throws SQLException {

        this.userDAO = (UserDAO) DAOFactory.getUserDAO(); // cast qui indique que l'objet retourné par DAOFactory.getUserDAO() doit être traité comme un objet de type UserDAO.
        this.passwordManagerDAO = (PasswordManager) DAOFactory.getPasswordManagerDAO();
    }


    //bouton login qui va enclencher l'évenement
    public void login(ActionEvent event) {

        if ((password_text.getText().isBlank()) || email_text.getText().isBlank()) {

            label_login.setText("Field cannot be blank");

        } else {

            checkUserExistence();
        }

    }


    // methode qui vérifie si un utilisateur existe dans la base de donnée
    public void checkUserExistence() {

        User user = userDAO.findUserByEmail(email_text.getText());

        if (user != null && user.getPassword().equals(password_text.getText())) {

            label_login.setText("Login successful");

            openListPassword(); // Redirection vers la page suivante

        } else {

            label_login.setText("Invalid email or password");
        }
}





    // methode qui permet d'ouvrir la liste des mots de passe enregistres
    @FXML
    private void openListPassword() {

        try {

            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader listPasswordFXML = new FXMLLoader(getClass().getResource("listPassword.fxml"));
            //loader.load() charge le fichier FXML
            Parent root = listPasswordFXML.load();

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("List of passwords");

            // Affichage de la fenêtre principale
            primaryStage.show();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    // Méthode pour afficher les mots de passe associés à l'utilisateur connecté
    private void searchIdentifiantByUser() throws SQLException {


        Map<Integer, PasswordManager> passwords = passwordManagerDAO.findByUserId(user.getId());

        // Ajouter les mots de passe au tableau
        for (PasswordManager password : passwords.values()) {

            listPasswordController.collectIdForListPassword(username, password, url, email);
        }
    }


    //bouton reset
    public void resetLogin(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        email_text.clear();
        username_text.clear();
        password_text.clear();
        label_login.setText("");
        url_text.setText("https://"); //va remettre le champs pré-écrit

    }


}




