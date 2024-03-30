package org.example.ex_application_bureau.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ex_application_bureau.Model.PasswordManager;
import org.example.ex_application_bureau.Model.User;

import java.io.*;
import java.net.URL;
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
    private Button refresh_button;

    @FXML
    private Label email_text;

    private ObservableList<PasswordManager> arrayListPassword;


    //la méthode initialize est utilisée pour configurer le tableView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        arrayListPassword = FXCollections.observableArrayList();

        username_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("username"));
        password_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("password"));
        url_column.setCellValueFactory(new PropertyValueFactory<PasswordManager, String>("url"));

        //selectCellInTableView();

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


//    public void selectCellInTableView() {
//
//        tableView.setOnMouseClicked(event -> {
//
//            if (event.getClickCount() == 2) { // si il y a un double click
//
//                // La valeur renvoyée par getSelectedItem() est stockée dans la variable selectedUser, qui est de type User.
//                PasswordManager selectedUser = tableView.getSelectionModel().getSelectedItem();
//
//                if (selectedUser != null) {
//
//                    // Récupérer les informations de la cellule
//                    String username = selectedUser.getUsername();
//                    String password = selectedUser.getPassword();
//                    String url = selectedUser.getUrl();
//
//                    // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
//                    FXMLLoader  passwordManagerFXML = new FXMLLoader(getClass().getResource("passwordManager.fxml"));
//
//                    //loader.load() charge le fichier FXML
//                    try {
//                        Parent root = passwordManagerFXML.load();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    // Récupérer le contrôleur de la deuxième fenêtre
//                    PasswordManager controller = passwordManagerFXML.getController();
//
//                    // Ouvrir une nouvelle fenêtre avec les informations de la cellule
//                    controller.openPasswordManager(username, password, url, email_text.getText());
//                }
//            }
//        });
//
//    }


    @FXML
    public void updateTableView(String username, String password, String url) {

        System.out.println("data to update : username : " + username  + "password:" + password + "url : " + url);

        System.out.println(arrayListPassword.toString());

        ObservableList<PasswordManager> currentTableData = tableView.getItems();

        for (PasswordManager user : currentTableData) {

            if (user.getUrl().equals(url)) {
                // Mise à jour des propriétés de l'objet existant au lieu de créer un nouvel objet
                user.setPassword(password);
                user.setUsername(username);
                break;
            }
        }

        tableView.setItems(currentTableData);
        tableView.refresh();

        System.out.println("Data in Table View updated: " + currentTableData);
    }


    @FXML
    private void refreshTableView(ActionEvent event) {

        ObservableList<PasswordManager> currentTableData = tableView.getItems();

        System.out.println("Data in Table View:");

        for (PasswordManager user : currentTableData) {
            System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", URL: " + user.getUrl());

            if (user.getUrl().equals("https://hi.fr")) {

                // Mise à jour des propriétés de l'objet existant au lieu de créer un nouvel objet
                user.setPassword("password");
                user.setUsername("username");

                System.out.println("Data in Table View updated: " + currentTableData);

                break;


            } else {

                System.out.println(" no data to refresh");
            }
        }

        tableView.setItems(currentTableData);
        tableView.refresh();

    }


}

