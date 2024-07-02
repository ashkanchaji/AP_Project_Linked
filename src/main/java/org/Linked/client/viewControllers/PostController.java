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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.Linked.server.Model.Post;
import org.Linked.server.Model.User;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextFlow postTextTFlow;

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
        highlightHashtags(post.getText(), postTextTFlow);
        if (post.getByteFilePath() != null && !post.getByteFilePath().isEmpty()){
            Media media = new Media(new File(post.getByteFilePath()).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            postMediaPlayer.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        }
        dateLabel.setText(post.getCreatedAt().toString());
    }

    private void highlightHashtags(String text, TextFlow textFlow) {
        // Define the regular expression pattern for hashtags
        Pattern pattern = Pattern.compile("\\B#\\w\\w+");
        Matcher matcher = pattern.matcher(text);

        int lastIndex = 0;
        while (matcher.find()) {
            // Create regular text nodes for text between hashtags (italic and larger)
            String plainText = text.substring(lastIndex, matcher.start());
            Text plainTextNode = new Text(plainText);
            plainTextNode.setStyle("-fx-font-style: italic; -fx-font-size: 14pt;"); // Set italic and larger size
            textFlow.getChildren().add(plainTextNode);

            // Create bold and blue text nodes for hashtags
            String hashtag = matcher.group();
            Text boldTextNode = new Text(hashtag);
            boldTextNode.setStyle("-fx-font-weight: bold; -fx-fill: #1b66bb; -fx-font-size: 12pt;"); // Set bold, blue, and same size
            textFlow.getChildren().add(boldTextNode);

            lastIndex = matcher.end();
        }

        // Append the remaining plain text (italic and larger)
        if (lastIndex < text.length()) {
            String remainingText = text.substring(lastIndex);
            Text remainingTextNode = new Text(remainingText);
            remainingTextNode.setStyle("-fx-font-style: italic; -fx-font-size: 14pt;"); // Set italic and larger size
            textFlow.getChildren().add(remainingTextNode);
        }
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
