package org.example.ex_application_bureau.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ex_application_bureau.Model.entity.PasswordManager;


public class PasswordManagerWindow {

    private static Stage openPasswordManagerStage;


    //methode qui permet d'ouvrir la fenêtre pour éditer l'email ou le mot de passe
    @FXML
    public static void openPasswordManager(PasswordManager identifiantSelected, ListPasswordController listPasswordControllerInstance) {

        if (openPasswordManagerStage == null) {

            try {

                // récupère l'URL du fichier FXML en fonction du chemin relatif spécifié
                FXMLLoader passwordManagerFXML = new FXMLLoader(PasswordManagerWindow.class.getResource("/org/example/ex_application_bureau/View/passwordManager.fxml"));

                //loader.load() charge le fichier FXML
                Parent root = passwordManagerFXML.load();

                // Récupérer le contrôleur
                PasswordManagerController controller = passwordManagerFXML.getController();

                //appel de la methode initialize() du controller PasswordManagerController et injecté les instanciations pour les réutiliser dans le controller
                controller.initialize(identifiantSelected, listPasswordControllerInstance);

                // Création d'une scène avec la racine (Root), et spécification des dimensions
                Scene scene = new Scene(root, 600, 400);

                openPasswordManagerStage = new Stage();

                // Configuration de la scène sur la fenêtre principale (primaryStage)
                openPasswordManagerStage.setScene(scene);

                //Définition du titre de la fenêtre principale
                openPasswordManagerStage.setTitle("Password Manager");

                // Ajoute un écouteur pour l'événement de fermeture de la fenêtre
                openPasswordManagerStage.setOnCloseRequest(closeEvent -> {

                    // Réinitialise la variable addPasswordStage à null lorsque la fenêtre est fermée
                    openPasswordManagerStage = null;

                });

                // Affichage de la fenêtre principale
                openPasswordManagerStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            // Si la fenêtre est déjà ouverte, la ramène simplement au premier plan
            openPasswordManagerStage.toFront();
        }

    }


}
