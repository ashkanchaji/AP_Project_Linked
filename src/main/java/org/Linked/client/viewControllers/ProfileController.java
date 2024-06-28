package org.Linked.client.viewControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.io.File;
import java.io.IOException;

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
    private Label currentEducationLabel;

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

    private String avatarAddress;
    private String bannerAddress;
    private String currentUserEmail;
    private String profileUserEmail;

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

//        currentUserEmail = "achaji2563@gmail.com";
//        // JWTController.getSubjectFromJwt(JWTController.getJwtKey())
//
//        HttpResponse userResponse;
//        try {
//            userResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + currentUserEmail, HttpMethod.GET, null, null);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (userResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode userJson = null;
//
//        try {
//            userJson = objectMapper.readTree(userResponse.getBody());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        JsonNode additionalName = userJson.get("additionalName");
//        JsonNode headline = userJson.get("headline");
//        JsonNode country = userJson.get("country");
//        JsonNode city = userJson.get("city");
//        JsonNode profession = userJson.get("profession");
//
//        firstNameEditTF.setText(userJson.get("firstName").asText());
//        additionalNameEditTF.setText(additionalName == null ? "" : additionalName.asText());
//        lastNameEditTF.setText(userJson.get("lastName").asText());
//        headlineEditTA.setText(headline == null ? "" : headline.asText());
//        countryEditTF.setText(country == null ? "" : country.asText());
//        cityEditTF.setText(city == null ? "" : city.asText());
//        professionEditTF.setText(profession == null ? "" : profession.asText());
//
//        avatarAddress = userJson.get("profilePicture") == null ? "" : userJson.get("profilePicture").asText();
//        bannerAddress = userJson.get("backgroundPicture") == null ? "" : userJson.get("backgroundPicture").asText();
//
//        if (!avatarAddress.isEmpty()){
//            Image avatar = new Image(avatarAddress);
//            profileAvatar.setImage(avatar);
//        }
//
//        if (!bannerAddress.isEmpty()){
//            Image banner = new Image(bannerAddress);
//            bannerImageView.setImage(banner);
//        }
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
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
//        if (selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//            profileAvatar.setImage(image);
//        }
        avatarAddress = selectedFile == null ? null : selectedFile.getAbsolutePath();
    }

    @FXML
    void on_chooseBannerButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
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

    }

}