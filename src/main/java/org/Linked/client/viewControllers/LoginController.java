package org.Linked.client.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button loginViewButton;

    @FXML
    private PasswordField password;

    @FXML
    private Button signupViewButton;

    @FXML
    private TextField userName;

    @FXML
    private VBox vboxCenter;

    @FXML
    void on_login_clicked(ActionEvent event) {
        userName.setText("ok");
    }

    @FXML
    void on_signupViewButton_clicked(ActionEvent event) {
        try {
            // Load the FXML file for the SignUp page
            Parent signupPage = FXMLLoader.load(getClass().getResource("/fxml/SignUpView.fxml"));

            // Create a new scene using the SignUp page
            Scene signupScene = new Scene(signupPage);

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) signupViewButton.getScene().getWindow();
            currentStage.setScene(signupScene);
//            currentStage.setFullScreen(true);
//            currentStage.setFullScreenExitHint("");
//            currentStage.setMaxHeight(1440);
//            currentStage.setMaxWidth(2560);
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


