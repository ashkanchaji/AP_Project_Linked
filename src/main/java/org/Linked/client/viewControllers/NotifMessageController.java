package org.Linked.client.viewControllers;


import io.github.gleidson28.GNAvatarView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.Linked.server.Model.Post;
import org.Linked.server.Model.User;

import java.nio.file.Paths;

public class NotifMessageController extends AbstractViewController {

    @FXML
    private GNAvatarView avatarPicture;

    @FXML
    private Label requestInfoLabel;

    @FXML
    private Label usernameLabel;

    private User notifSender;

    @FXML
    void on_avatarPicture_clicked(MouseEvent event) {
        ProfileController.setProfileUserEmail(notifSender.getEmail());
        switchScenes("/fxml/profileView.fxml", avatarPicture);
    }

    @FXML
    void on_usernameLabel_clicked(MouseEvent event) {
        on_avatarPicture_clicked(event);
    }

    public void initializeNotifMess(User sender){
        notifSender = sender;

        Image image;
        if (sender.getProfilePicture() == null || sender.getProfilePicture().isEmpty()){
            image = new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString());
        } else {
            image = new Image(Paths.get(sender.getProfilePicture()).toUri().toString());
        }

        avatarPicture.setImage(image);
        requestInfoLabel.setText(sender.getEmail().split("@")[0] + " posted a  new post ");
        usernameLabel.setText(sender.getEmail().split("@")[0]);

    }

}


