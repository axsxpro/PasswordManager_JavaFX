package org.example.ex_application_bureau;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


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


    public void resetLogin(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        email_text.clear();
        username_text.clear();
        password_text.clear();
        label_login.setText("");
        url_text.setText("https://"); //va remettre le champs pré-écrit

    }


    public void login(ActionEvent event) {

        if ((username_text.getText().isBlank() || password_text.getText().isBlank()) || url_text.getText().isBlank() || email_text.getText().isBlank()) {

            label_login.setText("Field cannot be blank");

        } else {

            label_login.setText("You are logged");

            openListPassword();

        }

    }


    private JSONArray saveUserInArray() {

        JSONArray jsonArray = null;

        try {

            // Récupérer la valeur de l'id password
            String password = password_text.getText();
            // Récupérer la valeur de l'id username
            String username = username_text.getText();
            // Récupérer la valeur de l'id url
            String url = url_text.getText();
            // Récupérer la valeur de l'id email
            String email = email_text.getText();

            // Hasher le mot de passe avec BCrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Créer un tableau associatif "tableValues" pour enregistrer les informations d'utilisateur
            // <String, String>: va contenir le type de la clé et le type de sa valeur
            Map<String, String> tableValues = Map.of("username", username, "password", hashedPassword, "url", url, "email", email);


            /* RECUPERATION DU CONTENU EXISTANT DU FICHIER savedPassword.json */

            // Créer un objet File pour représenter le fichier json + indication de la route ou se trouve le fichier
            File JsonFile = new File("src/main/resources/savedPassword.json");

            // Vérifier si le fichier Json existe déjà afin d'éviter de recréer un tableau principal, le but est de ré-écrire à la suite
            if (JsonFile.exists()) {

                // Lire le contenu actuel du fichier Json
                BufferedReader readJsonFile = new BufferedReader(new FileReader(JsonFile));

                // StringBuilder est utilisé pour construire une seule chaîne de caractères à partir des lignes lues avec BufferedReader.
                StringBuilder jsonFileContent = new StringBuilder();
                String line;

                // grace au Bufferedreader je lis chaque ligne de mon JsonFile
                while ((line = readJsonFile.readLine()) != null) {

                    //chaque ligne sera ajouté dans mon nouveau contenu jsonFile
                    jsonFileContent.append(line);
                }
                readJsonFile.close();


                // Création du tableau JSON
                if (jsonFileContent.isEmpty()) {

                    // Si le fichier est vide, commencez un nouveau tableau JSON
                    jsonArray = new JSONArray();

                } else {

                    // Sinon, utilisez le contenu existant comme tableau JSON
                    /* REECRITURE DU CONTENU EXISTANT DU FICHIER savedPassword.json AVEC NOUVELLES DONNEES */
                    // Parser le contenu JSON existant

                /* le processus de "parsing" permet de prendre une représentation textuelle des données
                (comme une chaîne JSON) et de la transformer en une structure de données que le programme peut
                comprendre et manipuler (comme un objet JSONArray dans ce cas).
                 */
                    jsonArray = new JSONArray(jsonFileContent.toString());

                }

                // Ajouter le nouvel utilisateur au tableau existant
                jsonArray.put(new JSONObject(tableValues));

                // Écrire le contenu mis à jour dans le fichier
                BufferedWriter writer = new BufferedWriter(new FileWriter(JsonFile));

                writer.write(jsonArray.toString(2)); // Le paramètre 2 pour l'indentation

                writer.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }


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


    // methode qui permet d'ouvrir la liste des utilisateurs
    @FXML
    private void openListPassword() {

        try {

            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader listPasswordFXML = new FXMLLoader(getClass().getResource("listPassword.fxml"));
            //loader.load() charge le fichier FXML
            Parent root = listPasswordFXML.load();

            // Récupérer le contrôleur de la deuxième fenêtre et le stocker dans une variable pour le réutiliser dans une autre méthode
            listPasswordController = listPasswordFXML.getController();

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("List of passwords");

            // Affichage de la fenêtre principale
            primaryStage.show();

            //lire le fichier Json poour afficher les données dans la listPassword
            readUsersFromJsonFile();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void readUsersFromJsonFile() {

        try {

            // stockage du tableau JSON dans une variable , on stock la méthode 'saveUserInArray'
            JSONArray usersJsonArray = saveUserInArray();

            for (int i = 0; i < usersJsonArray.length(); i++) {

                // récupération des tableaux associatifs cad les objets qu'on a précédement convertit en JSON
                JSONObject jsonObject = usersJsonArray.getJSONObject(i);
                //récupération des clés
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String url = jsonObject.getString("url");
                String email = jsonObject.getString("email");


                // Ajouter uniquement les utilisateurs avec le nom d'utilisateur correspondant
                if (email_text.getText().equals(email)) {

                    //appel du controleur dans ListPasswordController
                    listPasswordController.collectIdForListPassword(username, password, url, email);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




