package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.Linked.server.Model.Post;
import org.Linked.server.Model.User;

import java.io.File;
import java.nio.file.Paths;

public class PostController extends AbstractViewController{

    @FXML
    private ImageView commentImageView;

    @FXML
    private Button commentLabel;

    @FXML
    private ImageView likeImageView;

    @FXML
    private Button likeLabel;

    @FXML
    private GNAvatarView poserAvatar;

    @FXML
    private MediaView postMediaPlayer;

    @FXML
    private TextArea postTextTA;

    @FXML
    private Label posterUserNameLabel;

    @FXML
    private Button showCommentsButton;

    @FXML
    private Button showLikesButton;

    @FXML
    private Label dateLabel;

    private String posterEmail;

    public void initializePostData (Post post, User user) {
        posterEmail = user.getEmail();
        if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()){
            poserAvatar.setImage(new Image(Paths.get(user.getProfilePicture()).toUri().toString()));
        } else {
            poserAvatar.setImage(new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString()));
        }
        posterUserNameLabel.setText(user.getEmail().split("@")[0]);
        postTextTA.setText(post.getText());
        if (post.getByteFilePath() != null && !post.getByteFilePath().isEmpty()){
            Media media = new Media(new File(post.getByteFilePath()).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            postMediaPlayer.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        }
        dateLabel.setText(post.getCreatedAt().toString());
    }

    @FXML
    void on_poserAvatar_clicked(MouseEvent event) {
        on_posterUserNameLabel_clicked(event);
    }

    @FXML
    void on_posterUserNameLabel_clicked(MouseEvent event) {
        ProfileController.setProfileUserEmail(posterEmail);
        switchScenes("/fxml/profileView.fxml", posterUserNameLabel);
    }

    @FXML
    void on_commentImageView_clicked(MouseEvent event) {

    }

    @FXML
    void on_commentLabel_clicked(ActionEvent event) {

    }

    @FXML
    void on_likeImageView_clicked(MouseEvent event) {

    }

    @FXML
    void on_likeLabel_clicked(ActionEvent event) {

    }

    @FXML
    void on_showCommentsButton_clicked(ActionEvent event) {

    }

    @FXML
    void on_showLikesButton_clicked(ActionEvent event) {

    }

}
