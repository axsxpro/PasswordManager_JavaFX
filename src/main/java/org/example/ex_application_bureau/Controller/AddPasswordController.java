package org.example.ex_application_bureau.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddPasswordController {

    @FXML
    private Label label_error;

    @FXML
    private Label label_validation;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button reset_button;

    @FXML
    private Button save_button;

    @FXML
    private TextField url_field;

    @FXML
    private TextField username_field;






    @FXML
    void saveNewPassword(ActionEvent event) {

    }

    @FXML
    void resetForm(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        url_field.clear();
        username_field.clear();
        password_field.clear();
        label_error.setText("");
        label_validation.setText("");

    }

}