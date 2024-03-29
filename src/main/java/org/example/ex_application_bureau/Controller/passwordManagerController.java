package org.example.ex_application_bureau.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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



    //Methode qui supprime les données dans le fichier JSON
    public void deleteDataInJsonFile() {

        JSONArray dataJsonArray = new JSONArray();

        try {
            // Chemin du fichier JSON
            File fileSavedPassword = new File("src/main/resources/savedPassword.json");

            // Vérifier si le fichier existe
            if (!fileSavedPassword.exists()) {
                System.out.println("File doesn't exist.");
                return;
            }

            // Lire le contenu actuel du fichier JSON
            String jsonFileContent = new String(Files.readAllBytes(Paths.get(fileSavedPassword.getAbsolutePath())));

            // Parser le contenu JSON existant
            if (!jsonFileContent.isEmpty()) {
                dataJsonArray = new JSONArray(jsonFileContent);
            }

            // Identifier l'objet à supprimer en fonction des critères
            for (int i = 0; i < dataJsonArray.length(); i++) {
                JSONObject jsonObject = dataJsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String url = jsonObject.getString("url");

                if (urlText_pm.getText().equals(url) && usernameText_pm.getText().equals(username) && passwordText_pm.getText().equals(password)) {

                    // Supprimer l'objet trouvé
                    dataJsonArray.remove(i);
                    System.out.println("Value deleted");
                    break;

                } else {

                    System.out.println("Value not found to delete in file JSON");
                }
            }

            // Écrire le contenu mis à jour dans le fichier JSON
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSavedPassword))) {
                writer.write(dataJsonArray.toString(2)); // Le paramètre 2 pour l'indentation
            }

            label_pm.setText("Username and password deleted");

            modify_button.setVisible(false);
            delete_button.setVisible(false);
            usernameText_pm.setVisible(false);
            passwordText_pm.setVisible(false);
            username_pm.setVisible(false);
            password_pm.setVisible(false);
            urlText_pm.setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //methode qui actionne le bouton delete
    @FXML
    public void deleteId(ActionEvent event) {


        deleteDataInJsonFile();

    }



    //Methode qui permet la MAJ dans le fichier JSON
    @FXML
    public void updateDataInJsonFile() {

        // Stockage des valeurs du fichier "file" dans un tableau
        JSONArray dataJsonArray = new JSONArray();


        try {

            // Chemin du fichier
            File fileSavedPassword = new File("src/main/resources/savedPassword.json");

            // Vérifier si le fichier existe
            if (!fileSavedPassword.exists()) {
                System.out.println("File doesn't exist.");
                return;
            }

            // Lire le contenu actuel du fichier JSON
            String jsonFileContent = new String(Files.readAllBytes(Paths.get(fileSavedPassword.getAbsolutePath())));

            // Parser le contenu JSON existant
            if (!jsonFileContent.isEmpty()) {

                dataJsonArray = new JSONArray(jsonFileContent);
            }



            // Parcourir le tableau JSON
            for (int i = 0; i < dataJsonArray.length(); i++) {

                JSONObject jsonObject = dataJsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String url = jsonObject.getString("url");
                String email = jsonObject.getString("email");

                if (urlText_pm.getText().equals(url) && emailText_pm.getText().equals(email)) {


                    if (!passwordField_pm.getText().isBlank() && !usernameField_pm.getText().isBlank()) {

                        // Remplacer le username actuel par le nouveau username dans le champs username
                        jsonObject.put("username", usernameField_pm.getText());

                        // Hashe le mdp
                        String newPassword = passwordField_pm.getText();
                        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                        // Remplacer le password actuel par le nouveau password
                        jsonObject.put("password", hashedPassword);

                        //apelle de la methode collectId pour mettre à jour l'interface
                        collectId(usernameField_pm.getText(), hashedPassword, url, emailText_pm.getText());


                        // Appeler la méthode updateTableView du contrôleur ListPasswordController
                        //listPasswordController.updateTableView(username, password, url);

                    }


                    // Mettre à jour le username si le champ n'est pas vide et correspond au username actuel
                    if (!usernameField_pm.getText().isBlank() && passwordField_pm.getText().isBlank()) {

                        // Remplacer le username actuel par le nouveau username dans le champs username
                        jsonObject.put("username", usernameField_pm.getText());

                        //apelle de la methode collectId pour mettre à jour l'interface
                        collectId(usernameField_pm.getText(), password, url, emailText_pm.getText());


                        //listPasswordController.updateTableView(usernameField_pm.getText(), password, url);

                    }

                    // Mettre à jour le password si le champ n'est pas vide et correspond au password actuel
                    if (!passwordField_pm.getText().isBlank() && usernameField_pm.getText().isBlank()) {

                        // Hashe le mdp
                        String newPassword = passwordField_pm.getText();
                        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                        // Remplacer le password actuel par le nouveau password
                        jsonObject.put("password", hashedPassword);

                        //apelle de la methode collectId pour mettre à jour l'interface
                        collectId(username, hashedPassword, url, emailText_pm.getText());


                       // listPasswordController.updateTableView(username, hashedPassword, url);

                    }

                    }
                }


            // Écrire le contenu mis à jour dans le fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSavedPassword))) {
                writer.write(dataJsonArray.toString(2)); // Le paramètre 2 pour l'indentation
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //methode qui actionne le button 'save'
    @FXML
    public void saveData(ActionEvent event) {


        if (usernameField_pm.getText().isBlank() && passwordField_pm.getText().isBlank()) {

            label_pm.setText("Field cannot be blank, enter a value or cancel");

        } else {


            // Remettre le texte du label d'alerte à une chaîne vide
            label_pm.setText("");


            //appel de la fonction permettant de mettre à jour son password ou son username dans le fichier JSON
            updateDataInJsonFile();

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