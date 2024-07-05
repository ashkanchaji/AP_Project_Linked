package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpHeaders;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Queue;

public class DMController extends AbstractViewController{

    @FXML
    private Label DMLimitLabel;

    @FXML
    private GridPane DMShowGridPane;

    @FXML
    private Button addFileButton;

    @FXML
    private Button addPhotoButton;

    @FXML
    private Button addVideoButton;

    @FXML
    private GNAvatarView currDMAvatarPicture;

    @FXML
    private Label currDMUsernameLabel;

    @FXML
    private Button goBackButton;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHbox;

    @FXML
    private TextArea newDMTextArea;

    @FXML
    private Button notificationButton;

    @FXML
    private HBox notificationHbox;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHbox;

    @FXML
    private Button searchButton;

    @FXML
    private HBox searchHbox;

    @FXML
    private Button sendDMButton;

    @FXML
    private Button settingButton;

    @FXML
    private HBox settingHbox;




    private final String THIS_USER_EMAIL = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    private static String DMedUser;

    private String videoFileBase64 = null;
    private String photoFileBase64 = null;
    private String pdfFileBase64 = null;

    private User DMUser;

    private ArrayList<DirectMessage> allDM;

    @FXML
    private void initialize() {
        newDMTextArea.setPromptText("Share an article, photo, video or idea with " + DMedUser.split("@")[0]);

        DMLimitLabel.setText("Your Massage text is too long.");
        DMLimitLabel.setVisible(false);

        videoFileBase64 = null;
        photoFileBase64 = null;
        pdfFileBase64 = null;

        HttpResponse dmUser;
        HttpResponse DMs;

        try {
            dmUser = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + DMedUser, HttpMethod.GET, null, null);
            DMs = HttpController.sendRequest(SERVER_ADDRESS + "/directMessage", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DMUser = gson.fromJson(dmUser.getBody(), User.class);
        allDM = gson.fromJson(DMs.getBody(), DM_LIST_TYPE);

        Image image;
        if (DMUser.getProfilePicture() == null || DMUser.getProfilePicture().isEmpty()) {
            image = new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString());
        } else {
            image = new Image(Paths.get(DMUser.getProfilePicture()).toUri().toString());
        }

        currDMAvatarPicture.setImage(image);
        currDMUsernameLabel.setText(DMedUser.split("@")[0]);

        DMShowGridPane.getChildren().clear();

        int row = 1;
        for (DirectMessage dm : allDM) {
            if (shouldDisplayDM(dm)){
                row = loadDM(dm, row);
            }
        }
    }

    private boolean shouldDisplayDM(DirectMessage directMessage) {
        return (directMessage.getPosterID().equals(THIS_USER_EMAIL) && directMessage.getReceiverID().equals(DMedUser)) ||
                directMessage.getPosterID().equals(DMedUser) && directMessage.getReceiverID().equals(THIS_USER_EMAIL);
    }

    private int loadDM(DirectMessage DM, int row) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostView.fxml"));
        try {
            StackPane DMView = loader.load();

            PostController controller = loader.getController();

            String userName = DM.getPosterID().equals(THIS_USER_EMAIL) ? THIS_USER_EMAIL : DMedUser;

            User user = null;
            HttpResponse userResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + userName, HttpMethod.GET, null, null);
            user = gson.fromJson(userResponse.getBody(), User.class);

            controller.initializePostData(DM, user, null, null, null);

            DMShowGridPane.add(DMView, 0, row);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return row + 1;
    }

    @FXML
    void on_sendDMButton_clicked(ActionEvent event) {
        if (newDMTextArea.getText() == null || newDMTextArea.getText().isEmpty()){
            DMLimitLabel.setText("Your comment should have text.");
            DMLimitLabel.setVisible(true);
            return;
        }
        java.sql.Date createdAt = Date.valueOf(LocalDate.now());
        DirectMessage DM = null;
        try {
            DM = new DirectMessage(THIS_USER_EMAIL, DMedUser, newDMTextArea.getText(), createdAt);
        } catch (CharacterNumberLimitException e) {
            DMLimitLabel.setVisible(true);
            return;
        }

        String DMJson = gson.toJson(DM);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/directMessage", HttpMethod.POST, DMJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (videoFileBase64 != null) {
            VideoFile videoFile = new VideoFile(DM.getDmId(), videoFileBase64);
            String videoJsonBody = gson.toJson(videoFile);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/videoFiles/" + DM.getDmId(), HttpMethod.POST, videoJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (photoFileBase64 != null) {
            PhotoFile photoFile = new PhotoFile(DM.getDmId(), photoFileBase64);
            String photoJsonBody = gson.toJson(photoFile);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/photoFiles/" + DM.getDmId(), HttpMethod.POST, photoJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (pdfFileBase64 != null) {
            PDFFile pdfFile = new PDFFile(DM.getDmId(), pdfFileBase64);
            String pdfJsonBody = gson.toJson(pdfFile);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/pdfFiles/" + DM.getDmId(), HttpMethod.POST, pdfJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        newDMTextArea.setText(null);
        initialize();
    }

    @FXML
    void on_addPhotoButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                photoFileBase64 = Base64.getEncoder().encodeToString(fileContent);
                videoFileBase64 = null;
                pdfFileBase64 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void on_addVideoButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                videoFileBase64 = Base64.getEncoder().encodeToString(fileContent);
                photoFileBase64 = null;
                pdfFileBase64 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void on_addFileButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                pdfFileBase64 = Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void on_goBackButton_clicked(ActionEvent event) {
        ProfileController.setProfileUserEmail(DMedUser);
        switchScenes("/fxml/profileView.fxml", goBackButton);
    }

    @FXML
    void on_currDMAvatarPicture_clicked(MouseEvent event) {
        ProfileController.setProfileUserEmail(DMedUser);
        switchScenes("/fxml/profileView.fxml", goBackButton);
    }

    @FXML
    void on_currDMUsernameLabel_clicked(MouseEvent event) {
        on_currDMAvatarPicture_clicked(event);
    }

    @FXML
    void on_notificationButton_clicked(ActionEvent event) {
        switchScenes("/fxml/NotificationView.fxml" , notificationButton);
    }

    @FXML
    void on_homeButton_clicked(ActionEvent event) {
        switchScenes("/fxml/HomeView.fxml" , homeButton);
    }

    @FXML
    void on_searchButton_clicked(ActionEvent event){
        switchScenes("/fxml/SearchView.fxml", searchButton);
    }

    @FXML
    void on_profileButton_clicked(ActionEvent event){
        ProfileController.setProfileUserEmail(THIS_USER_EMAIL);
        switchScenes("/fxml/profileView.fxml", profileButton);
    }

    @FXML
    void on_logoutButton_clicked(ActionEvent event) {
        switchScenes("/fxml/LoginView.fxml" , settingButton );
    }

    public static String getDMedUser() {
        return DMedUser;
    }

    public static void setDMedUser(String dMedUser) {
        DMedUser = dMedUser;
    }
}
