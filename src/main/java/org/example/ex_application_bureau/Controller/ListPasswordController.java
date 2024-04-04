package org.example.ex_application_bureau.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.ex_application_bureau.Model.PasswordManager;
import org.example.ex_application_bureau.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


public class ListPasswordController implements Initializable {

    @FXML
    private TableView<PasswordManager> tableView;

    @FXML
    private TableColumn<PasswordManager, String> password_column;

    @FXML
    private TableColumn<PasswordManager, String> url_column;

    @FXML
    private TableColumn<PasswordManager, String> username_column;

    @FXML
    private Button newPassword_button;

    @FXML
    private Label email_text;

    private ObservableList<PasswordManager> arrayListPassword;

    private PasswordManager identifiantSelected;


    //la méthode initialize est utilisée pour configurer le tableView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        arrayListPassword = FXCollections.observableArrayList();

        //informations que l'on souhaite afficher dans la vue
        username_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("username"));
        password_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("password"));
        url_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("url"));

        selectCellInTableView();

    }


    //collecter les données pour afficher dans le tableau des mots de passe
    public void collectIdForListPassword(int idPasswordManager, String username, String password, String url, User idUser) {

        //ajout des valeurs dans les colonnes
        arrayListPassword.add(new PasswordManager(idPasswordManager, username, password, url, idUser));

        // Mettre à jour le TableView
        tableView.setItems(arrayListPassword);

        //ajout de l'email dans le label
        //email_text.setText();

    }


    public void selectCellInTableView() {

        tableView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { // si il y a un double click

                // La valeur renvoyée par getSelectedItem() est stockée dans la variable selectedUser, qui est de type PasswordManager.
                identifiantSelected = tableView.getSelectionModel().getSelectedItem();

                if (identifiantSelected != null) {

                    // identifiantSelected : identifiant sélectionné dans la cellule
                    // this: instantiation actuelle de ListPasswordController
                    PasswordManagerWindow.openPasswordManager(identifiantSelected, this);

                }
            }
        });

    }


    @FXML
    public void updateTableView(PasswordManager updatedIdentifier) {

        int idPM = updatedIdentifier.getIdPasswordManager();
        String username = updatedIdentifier.getUsername();
        String password = updatedIdentifier.getPassword();
        String url = updatedIdentifier.getUrl();

        System.out.println("Data to update :  id: " + idPM + " , username : " + username + ", password:" + password + " , url : " + url);

        for (PasswordManager identifier : arrayListPassword) {

            System.out.println("Data in array :  Id: " + identifier.getIdPasswordManager() + ", Username: " + identifier.getUsername() + ", Password: " + identifier.getPassword() + ", URL: " + identifier.getUrl());
        }

        // Parcourir la liste arrayListPassword pour mettre à jour les données
        for (PasswordManager identifier : arrayListPassword) {

            if (identifier.getIdPasswordManager() == (idPM)) {
                identifier.setPassword(password);
                identifier.setUsername(username);
                break;
            }
        }

        for (PasswordManager identifier : arrayListPassword) {

            System.out.println("Data in array after update :  Id: " + identifier.getIdPasswordManager() + ", Username: " + identifier.getUsername() + ", Password: " + identifier.getPassword() + ", URL: " + identifier.getUrl());
        }

        // Mettre à jour le TableView directement à partir de la liste mise à jour
        tableView.refresh();
    }


    public void deleteFromTableView(int id) {

        //parcourir les éléments de la liste arrayListPassword.
        Iterator<PasswordManager> iterator = arrayListPassword.iterator();

        //boucle while qui se poursuivra tant qu'il y a des éléments à parcourir dans la liste
        while (iterator.hasNext()) {

            //récupération de chaque élement de la liste
            PasswordManager identifier = iterator.next();

            // si l'identifiant de l'élément actuel correspond à l'identifiant passé en paramètre
            if (identifier.getIdPasswordManager() == id) {

                //alors suppression de l'élément
                iterator.remove();
                break;
            }
        }

        tableView.refresh();
    }


    //button qui permet d'ouvrir la fenêtre d'ajout d'un nouveau mot de passe
    @FXML
    private void openAddPassWindow(ActionEvent event) {

        try {

            // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
            FXMLLoader addPasswordFXML = new FXMLLoader(getClass().getResource("/org/example/ex_application_bureau/View/addPassword.fxml"));

            //loader.load() charge le fichier FXML
            Parent root = addPasswordFXML.load();

            // Création d'une scène avec la racine (Root), et spécification des dimensions
            Scene scene = new Scene(root, 600, 400);

            Stage primaryStage = new Stage();

            // Configuration de la scène sur la fenêtre principale (primaryStage)
            primaryStage.setScene(scene);

            //Définition du titre de la fenêtre principale
            primaryStage.setTitle("Add new password");

            // Affichage de la fenêtre principale
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}

