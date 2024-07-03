package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
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

    @FXML
    private VBox postRootVbox;

    private String posterEmail;
    private final String LOGGED_USER = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    private String postID;

    private ArrayList<User> likerUsers = new ArrayList<>();

    @FXML
    public void initialize () {
        likeLabel.setText(countLikes() + " Likes");
    }

    public void initializePostData(Post post, User user) {
        initialize();
        posterEmail = user.getEmail();
        postID = post.getPostId();
        if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
            poserAvatar.setImage(new Image(Paths.get(user.getProfilePicture()).toUri().toString()));
        } else {
            poserAvatar.setImage(new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString()));
        }
        posterUserNameLabel.setText(user.getEmail().split("@")[0]);

        try {
            HttpResponse videoResponse = HttpController.sendRequest(SERVER_ADDRESS + "/videoFiles/" + post.getPostId(), HttpMethod.GET, null, null);
            if (videoResponse.getBody() == null || videoResponse.getBody().equals("No such video file found!")) {
                Node stackPane = null;
                for (Node node : postRootVbox.getChildren()) {
                    if (node instanceof StackPane) {
                        stackPane = node;
                        ((StackPane) node).getChildren().clear();
                        break;
                    }
                }

                HttpResponse photoResponse = HttpController.sendRequest(SERVER_ADDRESS + "/photoFiles/" + post.getPostId(), HttpMethod.GET, null, null);

                if (!(photoResponse.getBody() == null || photoResponse.getBody().equals("No such photo file found!"))) {
                    PhotoFile photoFile = gson.fromJson(photoResponse.getBody(), PhotoFile.class);

                    // Save photo file locally
                    byte[] photoData = Base64.getDecoder().decode(photoFile.getPhotoFile());
                    String photoFilePath = saveFileLocally(photoData, post, ".jpg", "photoFilePosts");

                    ImageView postPhotoIV = new ImageView(new File(photoFilePath).toURI().toString());
                    postPhotoIV.setPreserveRatio(true);
                    postPhotoIV.setFitHeight(((StackPane) stackPane).getPrefHeight());
                    postPhotoIV.setFitWidth(((StackPane) stackPane).getPrefWidth());

                    ((StackPane) stackPane).getChildren().add(postPhotoIV);
                } else {
                    postRootVbox.getChildren().remove(stackPane);
                }
            } else {
                VideoFile videoFile = gson.fromJson(videoResponse.getBody(), VideoFile.class);

                // Save video file locally
                byte[] videoData = Base64.getDecoder().decode(videoFile.getVideoFile());
                String videoFilePath = saveFileLocally(videoData, post, ".mp4", "videoFilePosts");

                // Create Media and MediaPlayer
                Media media = new Media(new File(videoFilePath).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                postMediaPlayer.setMediaPlayer(mediaPlayer);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.0);
                mediaPlayer.setAutoPlay(true);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        highlightHashtags(post.getText(), postTextTFlow);
        dateLabel.setText(post.getCreatedAt().toString());
    }


    private String saveFileLocally(byte[] fileData, Post post, String format, String folderName) {
        String fileName = post.getPostId() + format; // You can use postId or another unique identifier here
        String filePath = "src/main/resources/" + folderName + "/" + fileName; // Set your desired save path

        // Ensure the directory exists
        Path directoryPath = Paths.get("src/main/resources/" + folderName);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
                return null; // Return null or handle the error as needed
            }
        }

        // Save the file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null or handle the error as needed
        }

        return filePath;
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
        Like like = new Like(LOGGED_USER, posterEmail, postID);

        String likeJson = gson.toJson(like);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/likes", HttpMethod.POST, likeJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialize();
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

    private int countLikes() {
        likerUsers.clear();

        HttpResponse likesResponse;
        HttpResponse allUsersResponse;

        try {
            likesResponse = HttpController.sendRequest(SERVER_ADDRESS + "/likes", HttpMethod.GET, null, null);
            allUsersResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Like> allLikes = gson.fromJson(likesResponse.getBody(), LIKE_LIST_TYPE);
        ArrayList<User> allUsers = gson.fromJson(allUsersResponse.getBody(), USER_LIST_TYPE);

        for (Like like : allLikes){
            if (like.getPostId().equals(postID)){
                for (User user : allUsers){
                    if (user.getEmail().equals(like.getLiker())){
                        likerUsers.add(user);
                    }
                }
            }
        }

        return likerUsers.size();
    }
}
