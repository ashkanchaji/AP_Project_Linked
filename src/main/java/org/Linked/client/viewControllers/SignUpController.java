package org.Linked.client.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField confirmPassTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private Button loginViewButton;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signUpViewButton;

    @FXML
    void on_loginViewButton_clicked(ActionEvent event) {
        try {
            // Load the FXML file for the SignUp page
            Parent signupPage = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));

            // Create a new scene using the SignUp page
            Scene signupScene = new Scene(signupPage);

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) loginViewButton.getScene().getWindow();
            currentStage.setScene(signupScene);
            //currentStage.setFullScreen(true);
//            currentStage.setMaxHeight(1440);
//            currentStage.setMaxWidth(2560);
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void on_signUpButton_clicked(ActionEvent event) {

    }

    @FXML
    void on_signUpViewButton_clicked(ActionEvent event) {

    }

}

