package org.example.ex_application_bureau.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountController {

    @FXML
    private Button button_reset;

    @FXML
    private Button create_Button;

    @FXML
    private TextField field_email;

    @FXML
    private PasswordField field_password;

    @FXML
    private Label label_error;




    public void addUser () {



    }





    @FXML
    void createUser(ActionEvent event) {

        if ((field_email.getText().isBlank()) || field_password.getText().isBlank()) {

            label_error.setText("Field cannot be blank");

        } else {

            addUser();
        }

    }


    @FXML
    void resetForm(ActionEvent event) {

        // Réinitialiser les champs du formulaire
        field_email.clear();
        field_password.clear();
        label_error.setText("");

    }



}
