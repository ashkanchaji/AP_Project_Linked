package org.Linked.client.viewControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class LoginController extends AbstractViewController {
    @FXML
    private Button loginButton;

    @FXML
    private Button loginViewButton;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button signupViewButton;

    @FXML
    private TextField emailTF;

    @FXML
    private VBox vboxCenter;

    @FXML
    private Label statusMassageLabel;

    @FXML
    void on_login_clicked(ActionEvent event) {
        HttpURLConnection connection = null;
        try {
            String email = emailTF.getText();
            String password = passwordTF.getText();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(Map.of("email", email, "password", password));

            String endpoint = "/login";
            URL url = new URL(SERVER_ADDRESS + endpoint);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successful login
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String tokenResponse = reader.readLine();
                    try (FileWriter fileWriter = new FileWriter("src/main/java/org/Linked/client/Token/UserJwtToken.txt")) {
                        fileWriter.write(tokenResponse);
                    }
                }
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // Unauthorized (401) - Handle invalid credentials
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String response = reader.readLine();
                    statusMassageLabel.setText(response); // Display error message from server
                    statusMassageLabel.setVisible(true);
                }
            } else {
                // Handle other HTTP response codes (e.g., 400, 500, etc.)
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String response = reader.readLine();
                    statusMassageLabel.setText("Error: " + responseCode + " - " + response); // Display error message
                    statusMassageLabel.setVisible(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @FXML
    void on_signupViewButton_clicked(ActionEvent event) {
        try {
            // Store the current scene dimensions
            Stage currentStage = (Stage) signupViewButton.getScene().getWindow();
            previousSceneWidth = currentStage.getWidth();
            previousSceneHeight = currentStage.getHeight();

            // Load the FXML file for the SignUp page
            Parent signupPage = FXMLLoader.load(getClass().getResource("/fxml/SignUpView.fxml"));

            // Create a new scene using the SignUp page
            Scene signupScene = new Scene(signupPage);

            // Set the scene size to match the previous scene size
            signupScene.setRoot(signupPage);
            currentStage.setScene(signupScene);

            // Apply the previous scene size
            currentStage.setWidth(previousSceneWidth);
            currentStage.setHeight(previousSceneHeight);

            // Alternatively, maximize the stage if needed
            // currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


