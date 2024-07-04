package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Model.Connect;
import org.Linked.server.Model.User;

import java.io.IOException;
import java.nio.file.Paths;

public class ConnectRequestController extends AbstractViewController{

    @FXML
    private ImageView acceptLabel;

    @FXML
    private GNAvatarView avatarPicture;

    @FXML
    private ImageView rejectLabel;

    @FXML
    private Label requestInfoLabel;

    @FXML
    private Label usernameLabel;

    private User senderUser;

    private Connect pendingConnect;

    private NotificationController notificationController;

    private String LOGGED_USER = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    public void initializeRequestData(Connect connect, User sender, NotificationController NC){
        senderUser = sender;
        pendingConnect = connect;
        notificationController = NC;

        Image image;
        if (sender.getProfilePicture() == null || sender.getProfilePicture().isEmpty()){
            image = new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString());
        } else {
            image = new Image(Paths.get(sender.getProfilePicture()).toUri().toString());
        }

        avatarPicture.setImage(image);
        usernameLabel.setText(sender.getEmail().split("@")[0]);
        requestInfoLabel.setText(connect.getNotes());
    }

    @FXML
    void on_acceptLabel_clicked(MouseEvent event) {
        pendingConnect.setPending(false);

        String connectJson = gson.toJson(pendingConnect);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/connect", HttpMethod.PUT, connectJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notificationController.initialize();
    }

    @FXML
    void on_rejectLabel_clicked(MouseEvent event) {
        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + senderUser.getEmail() + "/" + LOGGED_USER, HttpMethod.DELETE, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notificationController.initialize();
    }


    @FXML
    void on_avatarPicture_clicked(MouseEvent event) {
        ProfileController.setProfileUserEmail(senderUser.getEmail());
        switchScenes("/fxml/profileView.fxml", avatarPicture);
    }

    @FXML
    void on_usernameLabel_clicked(MouseEvent event) {
        on_avatarPicture_clicked(event);
    }

}