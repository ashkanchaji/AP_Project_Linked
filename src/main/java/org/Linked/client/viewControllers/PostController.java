package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.Linked.client.FXModels.ProfileSearchCell;
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

    private ArrayList<User> allUsers;

    private ListView<User> likesListView;
    private ObservableList<User> likerUsers;
    private VBox likesVbox;

    @FXML void initialize() {
        likeLabel.setText(countLikes() + " Likes");
    }

    public void initializePostData(Post post, User user, VBox likesVbox, ListView<User> likesListView, ObservableList<User> likerUsers) {
        this.likesListView = likesListView;
        this.likerUsers = likerUsers;
        this.likesVbox = likesVbox;

        postID = post instanceof Comment ? ((Comment) post).getCommentId() : post.getPostId();

        likeLabel.setText(countLikes() + " Likes");
        posterEmail = user.getEmail();
        if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
            poserAvatar.setImage(new Image(Paths.get(user.getProfilePicture()).toUri().toString()));
        } else {
            poserAvatar.setImage(new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString()));
        }

        if (post instanceof Comment) {
            commentLabel.setVisible(false);
            commentLabel.setDisable(true);

            commentImageView.setVisible(false);
            commentLabel.setDisable(true);

            showCommentsButton.setVisible(false);
            showCommentsButton.setDisable(true);

            posterUserNameLabel.setText(user.getEmail().split("@")[0] + "'s comment on " +
                    ((Comment) post).getRepliedUser().split("@")[0] + "'s post");
        } else if (post instanceof DirectMessage) {
            for (Node node : postRootVbox.getChildren()){
                if (node instanceof HBox) {
                    for (Node node1 : ((HBox) node).getChildren()){
                        if (node1 instanceof Button) {
                            postRootVbox.getChildren().remove(node);
                            break;
                        }
                    }
                }
            }
            posterUserNameLabel.setText(user.getEmail().split("@")[0]);
//            commentLabel.setVisible(false);
//            commentLabel.setDisable(true);
//
//            commentImageView.setVisible(false);
//            commentImageView.setDisable(true);
//
//            showCommentsButton.setVisible(false);
//            showCommentsButton.setDisable(true);
//
//            likeLabel.setVisible(false);
//            likeLabel.setDisable(true);
//
//            likeImageView.setVisible(false);
//            l
        } else {
            posterUserNameLabel.setText(user.getEmail().split("@")[0]);
        }

        try {
            HttpResponse videoResponse = HttpController.sendRequest(SERVER_ADDRESS + "/videoFiles/" + postID, HttpMethod.GET, null, null);
            if (videoResponse.getBody() == null || videoResponse.getBody().equals("No such video file found!")) {
                Node stackPane = null;
                for (Node node : postRootVbox.getChildren()) {
                    if (node instanceof StackPane) {
                        stackPane = node;
                        ((StackPane) node).getChildren().clear();
                        break;
                    }
                }

                HttpResponse photoResponse = HttpController.sendRequest(SERVER_ADDRESS + "/photoFiles/" + postID, HttpMethod.GET, null, null);

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
        CommentController.setCommentedPostID(postID);
        CommentController.setCommentedPosterID(posterEmail);
        switchScenes("/fxml/CommentView.fxml", commentImageView);
    }

    @FXML
    void on_commentLabel_clicked(ActionEvent event) {
        on_showCommentsButton_clicked(event);
    }

    @FXML
    void on_likeImageView_clicked(MouseEvent event) {
        ArrayList<Like> likes = getAllLikes();

        boolean shouldAdd = true;

        Like userLike = null;
        for (Like like : likes){
            if (like.getPostId().equals(postID) && like.getLiker().equals(LOGGED_USER)){
                shouldAdd = false;
                userLike = like;
                break;
            }
        }

        if (shouldAdd) {
            Like like = new Like(LOGGED_USER, posterEmail, postID);
            String likeJson = gson.toJson(like);

            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/likes", HttpMethod.POST, likeJson, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Image image = new Image(Paths.get("src/main/resources/Images/Icons/LikeColored.png").toUri().toString());
            likeImageView.setImage(image);
        } else {
            String likeJson = gson.toJson(userLike);
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/likes/" + userLike.getPostId(), HttpMethod.DELETE, likeJson, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = new Image(Paths.get("src/main/resources/Images/Icons/Like.png").toUri().toString());
            likeImageView.setImage(image);
        }

        initialize();
    }

    @FXML
    void on_likeLabel_clicked(ActionEvent event) {
        on_showLikesButton_clicked(event);
    }

    @FXML
    void on_showCommentsButton_clicked(ActionEvent event) {
        CommentController.setCommentedPostID(postID);
        CommentController.setCommentedPosterID(posterEmail);
        switchScenes("/fxml/CommentView.fxml", showCommentsButton);
    }

    @FXML
    void on_showLikesButton_clicked(ActionEvent event) {
        likerUsers.clear();

        likesVbox.setVisible(true);

        likesListView.setCellFactory(
                new Callback<ListView<User>, ListCell<User>>() {
                    @Override
                    public ListCell<User> call(ListView<User> param) {
                        return new ProfileSearchCell();
                    }
                }
        );

        // Bind the ListView's height to the total height of its cells
        likesListView.prefHeightProperty().bind(Bindings.size(likerUsers).multiply(80));
        likesListView.setOnMouseClicked(this::handleListViewClick);

        HttpResponse allUsersResponse;
        HttpResponse likesResponse;
        try {
            allUsersResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
            likesResponse = HttpController.sendRequest(SERVER_ADDRESS + "/likes", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (allUsersResponse.getStatusCode() != 200 || likesResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        allUsers = gson.fromJson(allUsersResponse.getBody(), USER_LIST_TYPE);
        ArrayList<Like> likes = gson.fromJson(likesResponse.getBody(), LIKE_LIST_TYPE);

        for (Like like : likes) {
            if (like.getPostId().equals(postID)){
                for (User user : allUsers) {
                    if (user.getEmail().equals(like.getLiker())){
                        likerUsers.add(user);
                    }
                }
            }
        }

        likesListView.setItems(likerUsers);
    }

    private void handleListViewClick(MouseEvent event) {
        User selectedUser = likesListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ProfileController.setProfileUserEmail(selectedUser.getEmail());
            switchScenes("/fxml/profileView.fxml", likesVbox);
        }
    }

    private int countLikes() {
        ArrayList<Like> allLikes = getAllLikes();

        int i = 0;
        for (Like like : allLikes){
            if (like.getPostId().equals(postID)){
                i++;

                if (like.getLiker().equals(LOGGED_USER)){
                    Image image = new Image(Paths.get("src/main/resources/Images/Icons/LikeColored.png").toUri().toString());
                    likeImageView.setImage(image);
                }
            }
        }

        return i;
    }

    private ArrayList<Like> getAllLikes(){
        HttpResponse likesResponse;

        try {
            likesResponse = HttpController.sendRequest(SERVER_ADDRESS + "/likes", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(likesResponse.getBody(), LIKE_LIST_TYPE);
    }
}
