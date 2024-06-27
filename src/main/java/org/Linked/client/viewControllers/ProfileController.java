package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ProfileController {

    @FXML
    private ImageView bannerImageView;

    @FXML
    private HBox homeHbox;

    @FXML
    private Button notificationButton;

    @FXML
    private HBox notificationHbox;

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
    private Button searchButton;

    @FXML
    private HBox searchHbox;

    @FXML
    private Button settingButton;

    @FXML
    private HBox settingHbox;

    @FXML
    private VBox tabVBox;

    @FXML
    private Button button;


    @FXML
    public void initialize() {
        bannerImageView.fitWidthProperty().bind(profileBP.widthProperty());
    }
}
