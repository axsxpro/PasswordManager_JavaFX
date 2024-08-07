package org.example.ex_application_bureau.Controller;

import javafx.scene.Node;
import org.example.ex_application_bureau.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.stage.Stage;
import org.example.ex_application_bureau.Model.dao.PasswordManagerDAO;
import org.example.ex_application_bureau.Model.dao.UserDAO;
import org.example.ex_application_bureau.Model.entity.PasswordManager;
import org.example.ex_application_bureau.Model.entity.User;
import org.mindrot.jbcrypt.BCrypt;


public class LoginController {


    @FXML
    private Button login_button;

    @FXML
    private Label password;

    @FXML
    private TextField password_text;

    @FXML
    private Label label_login;

    @FXML
    private Label label_valid;

    @FXML
    private Button reset_button;

    @FXML
    private TextField email_text;

    @FXML
    private Label label_NoAccount;

    @FXML
    private Hyperlink hyperLink_createAccount;

    private List<Map<String, String>> arrayUsers = new ArrayList<>();

    private UserDAO userDAO;
    private PasswordManagerDAO passwordManagerDAO;

    private User currentUser;

    private ListPasswordController listPasswordController;


    //Ce constructeur est appelé automatiquement lors de la création d'une instance de LoginController
    public LoginController() throws SQLException {

        this.userDAO = (UserDAO) DAOFactory.getUserDAO(); // cast qui indique que l'objet retourné par DAOFactory.getUserDAO() doit être traité comme un objet de type UserDAO.
        this.passwordManagerDAO = (PasswordManagerDAO) DAOFactory.getPasswordManagerDAO();
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

        //vérification de l'user par son email dans la bdd
        User user = userDAO.findUserByEmail(email_text.getText());

        // si l'utilisateur existe
        if (user != null) {

            // Récupérer le mot de passe hashé de l'utilisateur
            String hashedPassword = user.getPassword();

            // BCrypt.checkpw: Vérifier si le mot de passe saisi correspond au mot de passe hashé (attention != BCrypt.hashpw)
            if (BCrypt.checkpw(password_text.getText(), hashedPassword)) {

                currentUser = user; // Stocker l'utilisateur connecté

                label_login.setText("");
                label_valid.setText("Login successful");

                openListPassword(); // Redirection vers la fenêtre list Password Manager

            } else {

                label_login.setText("Invalid email or password");
            }

        } else {

            label_login.setText("No existing account");
        }

    }


    // methode qui permet d'ouvrir la fenêtre contenant la liste des mots de passe enregistrés
    @FXML
    private void openListPassword() {

        try {

            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader listPasswordFXML = new FXMLLoader(getClass().getResource("/org/example/ex_application_bureau/View/listPassword.fxml"));
            //loader.load() charge le fichier FXML
            Parent root = listPasswordFXML.load();

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            // Récupérer le contrôleur de la deuxième fenêtre et le stocker dans une variable pour le réutiliser dans une autre méthode
            // permet d'initialiser l'attribut 'listPasswordController'
            listPasswordController = listPasswordFXML.getController();

            // Appel de la méthode initializeCurrentUser pour configurer les données nécessaires concernant l'utilisateur connecté
            listPasswordController.initializeCurrentUser(currentUser);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("List of passwords");

            // Affichage de la fenêtre principale
            primaryStage.show();

            //appel de la methode pour charger la liste
            searchIdentifiantByUser();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    // Méthode pour afficher les mots de passe associés à l'utilisateur connecté
    private void searchIdentifiantByUser() throws SQLException {

        if (currentUser != null) {

            //récupération de tous les identifiants selon l'id de l'utilisateur connecté
            Map<Integer, PasswordManager> identifiants = passwordManagerDAO.findByIdUser(currentUser.getIdUser());

            // Ajouter les identifiants au tableView
            for (PasswordManager identifiant : identifiants.values()) {

                //appel de la methode 'collectIdForListPassword' dans la class listPasswordController pour afficher les données dans le tableView
                listPasswordController.collectIdForListPassword(

                        identifiant.getIdPasswordManager(),
                        identifiant.getUsername(),
                        identifiant.getPassword(),
                        identifiant.getUrl(),
                        identifiant.getIdUser()
                );
            }


        } else {

            System.err.println("No user logged in");
        }

    }


    @FXML
    public void openCreateAccount(ActionEvent event) {

        try {

            // Récupérer la scène actuelle
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Fermer la fenêtre de la scène actuelle
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.close();


            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader createAccountFXML = new FXMLLoader(getClass().getResource("/org/example/ex_application_bureau/View/createAccount.fxml"));
            //loader.load() charge le fichier FXML
            Parent root = createAccountFXML.load();

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("Create an account");

            // Affichage de la fenêtre principale
            primaryStage.show();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    //bouton reset
    public void resetLogin(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        email_text.clear();
        password_text.clear();
        label_login.setText("");
        label_valid.setText("");

    }


}




