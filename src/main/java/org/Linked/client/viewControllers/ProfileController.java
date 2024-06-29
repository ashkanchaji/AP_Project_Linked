package org.Linked.client.viewControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.client.viewControllers.Utils.UserTypeAdapter;
import org.Linked.server.Model.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ProfileController extends AbstractViewController{

    @FXML
    private TextField additionalNameEditTF;

    @FXML
    private ImageView bannerImageView;

    @FXML
    private Button cancelInfoButton;

    @FXML
    private Button chooseAvatarButton;

    @FXML
    private Button chooseBannerButton;

    @FXML
    private Label cityAndCountryLabel;

    @FXML
    private TextField cityEditTF;

    @FXML
    private Button connectButton;

    @FXML
    private TextField countryEditTF;

    @FXML
    private Label professionLabel;

    @FXML
    private Button editInfoButton;

    @FXML
    private TextField firstNameEditTF;

    @FXML
    private Button followButton;

    @FXML
    private Label fullNameLabel;

    @FXML
    private TextArea headLineTA;

    @FXML
    private TextArea headlineEditTA;

    @FXML
    private RadioButton hiringRadioBtn;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHbox;

    @FXML
    private RadioButton jobRadioBtn;

    @FXML
    private TextField lastNameEditTF;

    @FXML
    private Button notificationButton;

    @FXML
    private HBox notificationHbox;

    @FXML
    private TextField professionEditTF;

    @FXML
    private GNAvatarView profileAvatar;

    @FXML
    private BorderPane profileBP;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHbox;

    @FXML
    private StackPane profileStackPane;

    @FXML
    private BorderPane rootBP;

    @FXML
    private Button saveInfoButton;

    @FXML
    private Button searchButton;

    @FXML
    private HBox searchHbox;

    @FXML
    private RadioButton servicesRadioBtn;

    @FXML
    private Button settingButton;

    @FXML
    private HBox settingHbox;

    @FXML
    private VBox tabVBox;

    @FXML
    private VBox editInfoVbox;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String avatarAddress;
    private String bannerAddress;
    private String currentUserEmail;
    private String profileUserEmail;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String firstName;
    private String lastName;
    private String password;
    private JsonNode additionalName;
    private JsonNode headline;
    private JsonNode country;
    private JsonNode city;
    private JsonNode profession;



    @FXML
    private final ToggleGroup status = new ToggleGroup();

    @FXML
    public void initialize() {
        bannerImageView.fitWidthProperty().bind(profileBP.widthProperty());
        jobRadioBtn.setToggleGroup(status);
        hiringRadioBtn.setToggleGroup(status);
        servicesRadioBtn.setToggleGroup(status);
        editInfoVbox.setVisible(false);
        editInfoVbox.setDisable(true);

        /* ___ GET USER INFO ___ */

        currentUserEmail = "achaji2563@gmail.com";
        // JWTController.getSubjectFromJwt(JWTController.getJwtKey())

        HttpResponse userResponse = getUserResponse();

        JsonNode userJson = getUserJson(userResponse);

        firstName = userJson.get("firstName").asText();
        lastName = userJson.get("lastName").asText();
        password = userJson.get("password").asText();
        additionalName = userJson.get("additionalName");
        headline = userJson.get("headline");
        country = userJson.get("country");
        String countryName = country == null ? "" : country.asText();
        city = userJson.get("city");
        String cityName = city == null ? "" : city.asText();
        profession = userJson.get("profession");

        firstNameEditTF.setText(firstName);
        additionalNameEditTF.setText(additionalName == null ? "" : additionalName.asText());
        lastNameEditTF.setText(lastName);
        headlineEditTA.setText(headline == null ? "" : headline.asText());
        countryEditTF.setText(countryName);
        cityEditTF.setText(cityName);
        professionEditTF.setText(profession == null ? "" : profession.asText());

        avatarAddress = userJson.get("profilePicture") == null ? null : userJson.get("profilePicture").asText();
        bannerAddress = userJson.get("backgroundPicture") == null ? null : userJson.get("backgroundPicture").asText();

        if (avatarAddress != null){
            Image avatar = new Image(Paths.get(avatarAddress).toUri().toString());
            profileAvatar.setImage(avatar);
        }

        if (bannerAddress != null){
            Image banner = new Image(Paths.get(bannerAddress).toUri().toString());
            bannerImageView.setImage(banner);
        }

        fullNameLabel.setText(firstName + " " + (additionalName == null ? "" : (additionalName.asText() + " ")) + lastName);
        headLineTA.setText(headline == null ? "No Headline." : headline.asText());
        professionLabel.setText(profession == null ? "No profession." : profession.asText());
        cityAndCountryLabel.setText(cityName + ", " + countryName);
    }

    @FXML
    void on_searchButton_clicked(ActionEvent event) {
        try {
            // Store the current scene dimensions
            Stage currentStage = (Stage) searchButton.getScene().getWindow();
            previousSceneWidth = currentStage.getWidth();
            previousSceneHeight = currentStage.getHeight();

            // Load the FXML file for the SignUp page
            Parent profilePage = FXMLLoader.load(getClass().getResource("/fxml/searchView.fxml"));

            // Create a new scene using the SignUp page
            Scene profileScene = new Scene(profilePage);

            // Set the scene size to match the previous scene size
            profileScene.setRoot(profilePage);
            currentStage.setScene(profileScene);

            // Apply the previous scene size
            currentStage.setWidth(previousSceneWidth);
            currentStage.setHeight(previousSceneHeight);

            // Alternatively, maximize the stage if needed
            // currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void on_cancelInfoButton_clicked(ActionEvent event) {
        editInfoVbox.setVisible(false);
        editInfoVbox.setDisable(true);
    }

    @FXML
    void on_chooseAvatarButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        avatarAddress = selectedFile == null ? null : selectedFile.getAbsolutePath();
    }

    @FXML
    void on_chooseBannerButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        bannerAddress = selectedFile == null ? null : selectedFile.getAbsolutePath();
    }

    @FXML
    void on_editInfoButton_clicked(ActionEvent event) {
        editInfoVbox.setDisable(false);
        editInfoVbox.setVisible(true);
    }

    @FXML
    void on_saveInfoButton_clicked(ActionEvent event) {
        String newAdditionalName = additionalNameEditTF.getText();
        String newHeadline = headlineEditTA.getText();
        String newCountry = countryEditTF.getText();
        String newCity = cityEditTF.getText();
        String newProfession = professionEditTF.getText();
        User user = new User(currentUserEmail, firstNameEditTF.getText(), lastNameEditTF.getText(), password,
                newAdditionalName, avatarAddress, bannerAddress, newHeadline, newCountry, newCity, newProfession,
                JWTController.getJwtKey());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
        String userJson = gson.toJson(user);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.PUT, userJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        editInfoVbox.setDisable(true);
        editInfoVbox.setVisible(false);
        initialize();
    }

    private HttpResponse getUserResponse(){
        HttpResponse userResponse;
        try {
            userResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + currentUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (userResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        return userResponse;
    }

    private JsonNode getUserJson(HttpResponse userResponse){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}