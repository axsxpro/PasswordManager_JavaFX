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

    private PasswordManager identifiantSelected;


    //la méthode initialize est utilisée pour configurer le tableView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        arrayListPassword = FXCollections.observableArrayList();

        //iformations que l'on souhaite afficher dans la vue
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

                    // identifiantSelected : identifiant selectionné dans la cellulle
                    // this: intanciation actuelle de ListPasswordController
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

        System.out.println("data to update : username : " + username + ", password:" + password + " , url : " + url );

        ObservableList<PasswordManager> currentTableView = tableView.getItems();

        for (PasswordManager identifier : currentTableView) {

            if (identifier.getIdUser().equals(idPM)) {

                // Mise à jour des propriétés de l'objet existant au lieu de créer un nouvel objet
                identifier.setPassword(password);
                identifier.setUsername(username);
                break;
            }
        }

        tableView.setItems(currentTableView);
        tableView.refresh();

        System.out.println("Data in Table View updated: " + currentTableView);
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

