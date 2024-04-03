package org.example.ex_application_bureau;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        this.primaryStage = stage; // Initialisez la référence à la fenêtre principale
        openLoginWindow();

    }

    // Méthode pour ouvrir la fenêtre de connexion
    public void openLoginWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        if (primaryStage == null) {
            primaryStage = new Stage();
        }

        primaryStage.setTitle("PasswordManager login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}