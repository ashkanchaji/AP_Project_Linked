package org.Linked.client.viewControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.Linked.client.viewControllers.Utils.EmailValidator;
import org.Linked.client.viewControllers.Utils.UserTypeAdapter;
import org.Linked.server.Controller.Exeptions.InvalidEmailException;
import org.Linked.server.Controller.Exeptions.InvalidPassException;
import org.Linked.server.Model.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SignUpController extends AbstractViewController{

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
    private Label statusMassageLabel;

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
//            currentStage.setFullScreen(true);
//            currentStage.setMaxHeight(1440);
//            currentStage.setMaxWidth(2560);
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void on_signUpButton_clicked(ActionEvent event) {
        try {
            statusMassageLabel.setStyle("-fx-text-fill: red;");
            statusMassageLabel.setVisible(false);

            if (textFieldsNotFull() || notValidEmail() || notMatchingPasswords()) {
                return;
            }

            String userAsBodyRequest = createUser();
            String endPoint = "/users";
            URL url = new URL(SERVER_ADDRESS + endPoint);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(userAsBodyRequest.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                statusMassageLabel.setText("User created successfully.");
                statusMassageLabel.setStyle("-fx-text-fill: #00c11d;");
                statusMassageLabel.setVisible(true);
            }

        } catch (InvalidEmailException e) {
            statusMassageLabel.setText("A User with this email already exists.");
            statusMassageLabel.setVisible(true);
        } catch (InvalidPassException e) {
            statusMassageLabel.setText("Password must be at least 8 characters containing letters and numbers.");
            statusMassageLabel.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean textFieldsNotFull() {
        TextField[] textFields = {emailTF, firstNameTF, lastNameTF, passwordTF, confirmPassTF};

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                statusMassageLabel.setText("Please fill all the fields.");
                statusMassageLabel.setVisible(true);
                return true;
            }
        }
        return false;
    }

    private boolean notValidEmail() {
        if (EmailValidator.isValidEmail(emailTF.getText())) {
            statusMassageLabel.setVisible(false);
            return false;
        } else {
            statusMassageLabel.setText("Invalid Email. Please try again.");
            statusMassageLabel.setVisible(true);
            return true;
        }
    }

    private boolean notMatchingPasswords() {
        if (passwordTF.getText().equals(confirmPassTF.getText())) {
            statusMassageLabel.setVisible(false);
            return false;
        } else {
            statusMassageLabel.setText("Passwords don't match. Try again.");
            statusMassageLabel.setVisible(true);
            return true;
        }
    }

    private String createUser() throws InvalidPassException, InvalidEmailException {
        User user = new User(emailTF.getText(), firstNameTF.getText(), lastNameTF.getText(), passwordTF.getText());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();

        return gson.toJson(user);
    }
}