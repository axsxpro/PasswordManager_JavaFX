package org.example.ex_application_bureau.Controller;


import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.ex_application_bureau.Model.DAOFactory;
import org.example.ex_application_bureau.Model.PasswordManager;
import org.example.ex_application_bureau.Model.PasswordManagerDAO;



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

    @FXML
    private ImageView img_eyeClosed;

    @FXML
    private ImageView img_eyeOpen;

    private PasswordManagerDAO passwordManagerDAO;

    private PasswordManager currentIdentifier;

    private ListPasswordController listPasswordController;


    public PasswordManagerController() throws SQLException {

        this.passwordManagerDAO = (PasswordManagerDAO) DAOFactory.getPasswordManagerDAO(); // cast qui indique que l'objet retourné par DAOFactory.getPasswordManagerDAO() doit être traité comme un objet de type PasswordManagerDAO.

    }


    // lorsque la fenêtre PasswordManager sera ouverte, on va initialiser les attributs et la methode collectId pour affichage des données
    public void initialize(PasswordManager identifiantSelected, ListPasswordController listPasswordControllerInstance) {

        currentIdentifier = identifiantSelected; //stockage de l'id sélectionné dans le tableView dans l'attribut

        listPasswordController = listPasswordControllerInstance; // stockage de l'instanciation actuel de la class listPasswordController dans l'attribut

        collectId(currentIdentifier.getUsername(), currentIdentifier.getPassword(), currentIdentifier.getUrl()); //initialisation des données sur la vue à l'ouverture de la fenêtre password manager

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
        img_eyeOpen.setVisible(true);
        img_eyeClosed.setVisible(false);

    }


    @FXML
    public void modifyData() throws Exception {

        if (currentIdentifier == null) {

            System.out.println("No identifier to update");
            return;

        } else {

            System.out.println("Data to modify :" + currentIdentifier);
        }


        String newUsername = usernameField_pm.getText();
        String newPassword = passwordField_pm.getText();


        // Vérifiez si le mot de passe a été modifié
        if (!newPassword.isBlank()) {

            Dotenv dotenv = Dotenv.configure().load();
            // Récupérer la clé secrète du fichier .env
            String secretKey = dotenv.get("SECRET_KEY");

            //crypter le mot de passe
            String encryptedPassword = EncryptionManagerController.encrypt(newPassword, secretKey);
            currentIdentifier.setPassword(encryptedPassword);

        }

        // Vérifiez si le nom d'utilisateur a été modifié
        if (!newUsername.isBlank()) {

            currentIdentifier.setUsername(newUsername);
        }


        // Maj de la base de données avec les modifications
        PasswordManager updatedIdentifier = passwordManagerDAO.update(currentIdentifier);

        if (updatedIdentifier != null) {

            System.out.println("Data updated successfully");

            // Mettre à jour la vue
            collectId(updatedIdentifier.getUsername(), updatedIdentifier.getPassword(), updatedIdentifier.getUrl());

            // Mettre à jour la liste des mots de passe dans le contrôleur ListPasswordController
            listPasswordController.updateTableView(updatedIdentifier);

        } else {

            System.out.println("Failed to update data");
        }

    }


    //methode qui actionne le button 'save' pour enregistrer les données
    @FXML
    public void saveData(ActionEvent event) throws Exception {


        if (usernameField_pm.getText().isBlank() && passwordField_pm.getText().isBlank()) {

            label_pm.setText("Field cannot be blank, enter a value or cancel");

        } else {

            modifyData();


            // Remettre le texte du label d'alerte à une chaîne vide
            label_pm.setText("");

            // Lors du click sur le bouton 'save' on active ou on désactive certaines fonctionnalités
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


    @FXML
    public void deleteData() {

        if (currentIdentifier == null) {

            System.out.println("No identifier to delete");
            return;

        }

        //récupération de l'id de l'identifiant actuel
        int idCurrentIdentifier = currentIdentifier.getIdPasswordManager();

        //supprimer l'idetifiant par son id en faisant appel à la méthode delete de la class PasswordManagerDAO
        passwordManagerDAO.delete(idCurrentIdentifier);

        //mise à jour des données dans la vue
        listPasswordController.deleteFromTableView(idCurrentIdentifier);


        label_pm.setText("Username and password deleted");

        modify_button.setVisible(false);
        delete_button.setVisible(false);
        usernameText_pm.setVisible(false);
        passwordText_pm.setVisible(false);
        username_pm.setVisible(false);
        password_pm.setVisible(false);
        urlText_pm.setVisible(false);
        img_eyeOpen.setVisible(false);
        img_eyeClosed.setVisible(false);

    }


    //methode qui actionne le bouton delete
    @FXML
    public void deleteId(ActionEvent event) {

        //appel de la méthode pour supprimer les données
        deleteData();

    }


    @FXML
    public void cancelAction(ActionEvent event) {

        // Remettre le texte du label à une chaîne vide
        label_pm.setText("");

        // Renvoie des parametres dans la methode pour ré-affichage des données
        collectId(usernameText_pm.getText(), passwordText_pm.getText(), urlText_pm.getText());

        // Lors du cancel, activation ou désactivation de certaines fonctionnalités
        save_button.setVisible(false);
        cancel_button.setVisible(false);
        modify_button.setVisible(true);
        delete_button.setVisible(true);

    }


    @FXML
    void showPassword(MouseEvent event) throws Exception {

        img_eyeOpen.setVisible(false);
        img_eyeClosed.setVisible(true);


        //récupérer le mot de passe de l'objet PasswordManager
        String encryptedPassword = currentIdentifier.getPassword();

        // Récupérer la clé secrète du fichier .env
        Dotenv dotenv = Dotenv.configure().load();
        String secretKey = dotenv.get("SECRET_KEY");

        // Décrypter le mot de passe
        String decryptedPassword = EncryptionManagerController.decrypt(encryptedPassword, secretKey);

        // Afficher le mot de passe décrypté
        passwordText_pm.setText(decryptedPassword);


    }


    @FXML
    public void hidePassword(MouseEvent event) {

        img_eyeOpen.setVisible(true);
        img_eyeClosed.setVisible(false);

        // Récupérer le mot de passe crypté actuel
        String encryptedPassword = currentIdentifier.getPassword();

        // Mettre à jour le champ de texte du mot de passe avec le mot de passe crypté
        passwordText_pm.setText(encryptedPassword);

    }


}