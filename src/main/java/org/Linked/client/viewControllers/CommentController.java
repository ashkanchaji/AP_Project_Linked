package org.Linked.client.viewControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

public class CommentController extends AbstractViewController{

    @FXML
    private Button addNewCommentButton;

    @FXML
    private Button addPhotoButton;

    @FXML
    private Button addVideoButton;

    @FXML
    private Label commentLimitLabel;

    @FXML
    private Button likesExitButton;

    @FXML
    private ListView<User> likesListView;

    @FXML
    private VBox likesVbox;

    @FXML
    private TextArea newPostTextArea;

    @FXML
    private GridPane postShowGridPane;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button goBackButton;

    private final ObservableList<User> likerUsers = FXCollections.observableArrayList();


    private final String THIS_USER_EMAIL = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    private String videoFileBase64 = null;
    private String photoFileBase64 = null;

    private ArrayList<User> allUsers;
    private ArrayList<Comment> allComments;
    private ArrayList<Follow> userFollows;

    private static String commentedPostID;
    private static String commentedPosterID;

    @FXML
    public void initialize() {
        likesVbox.setVisible(false);

        newPostTextArea.setPromptText("Share an article, photo, video or idea on " + commentedPosterID.split("@")[0] + "'s post.");

        commentLimitLabel.setText("Your post text is too long.");
        commentLimitLabel.setVisible(false);

        videoFileBase64 = null;
        photoFileBase64 = null;

        HttpResponse users;
        HttpResponse Comments;
        HttpResponse follows;

        try {
            users = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
            Comments = HttpController.sendRequest(SERVER_ADDRESS + "/comments", HttpMethod.GET, null, null);
            follows = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + THIS_USER_EMAIL, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allUsers = gson.fromJson(users.getBody(), USER_LIST_TYPE);
        allComments = gson.fromJson(Comments.getBody(), COMMENT_LIST_TYPE);
        userFollows = gson.fromJson(follows.getBody(), FOLLOW_LIST_TYPE);

        // Reverse the list of posts
        Collections.reverse(allComments);

        // Clear the grid pane before adding new posts
        postShowGridPane.getChildren().clear();

        int row = 1;
        for (Comment comment : allComments) {
            if (shouldDisplayComment(comment)) {
                row = loadPost(comment, row);
            }
        }
    }

    private boolean shouldDisplayComment(Comment comment) {
        if (comment.getUserId().equals(THIS_USER_EMAIL) && comment.getPostId().equals(commentedPostID)) {
            return true;
        }
        for (Follow follow : userFollows) {
            if (comment.getUserId().equals(follow.getFollowing()) && comment.getPostId().equals(commentedPostID)) {
                return true;
            }
        }
        return false;
    }

    private int loadPost(Comment comment, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostView.fxml"));
            StackPane postView = loader.load();

            PostController controller = loader.getController();

            User postUser = null;
            for (User user : allUsers) {
                if (user.getEmail().equals(comment.getUserId())) {
                    postUser = user;
                    controller.initializePostData(comment, postUser, likesVbox, likesListView, likerUsers);
                    break;
                }
            }

            postShowGridPane.add(postView, 0, row);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return row + 1;
    }

    @FXML
    void on_addNewCommentButton_clicked(ActionEvent event) {
        if (newPostTextArea.getText() == null || newPostTextArea.getText().isEmpty()){
            commentLimitLabel.setText("Your comment should have text.");
            commentLimitLabel.setVisible(true);
            return;
        }
        java.sql.Date createdAt = Date.valueOf(LocalDate.now());
        Comment comment = null;
        try {
            comment = new Comment(commentedPostID, THIS_USER_EMAIL, newPostTextArea.getText(), 0, 0, createdAt, 0, commentedPosterID);
        } catch (CharacterNumberLimitException e) {
            commentLimitLabel.setVisible(true);
            return;
        }

        String commentJson = gson.toJson(comment);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/comments", HttpMethod.POST, commentJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (videoFileBase64 != null) {
            VideoFile videoFile = new VideoFile(comment.getPostId(), videoFileBase64);
            String videoJsonBody = gson.toJson(videoFile);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/videoFiles/" + comment.getCommentId(), HttpMethod.POST, videoJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (photoFileBase64 != null) {
            PhotoFile photoFile = new PhotoFile(comment.getPostId(), photoFileBase64);
            String photoJsonBody = gson.toJson(photoFile);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/photoFiles/" + comment.getCommentId(), HttpMethod.POST, photoJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        newPostTextArea.setText(null);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void on_goBackButton_clicked(ActionEvent event) {
        switchScenes("/fxml/HomeView.fxml", goBackButton);
    }

    @FXML
    void on_likesExitButton_clicked(ActionEvent event) {
        likesVbox.setVisible(false);
    }

    @FXML
    void on_profileButton_clicked(ActionEvent event) {
        switchScenes("/fxml/profileView.fxml", profileButton);
    }

    @FXML
    void on_searchButton_clicked(ActionEvent event) {
        switchScenes("/fxml/SearchView.fxml", searchButton);
    }


    public static String getCommentedPostID() {
        return commentedPostID;
    }

    public static void setCommentedPostID(String postID) {
        commentedPostID = postID;
    }

    public static String getCommentedPosterID() {
        return commentedPosterID;
    }

    public static void setCommentedPosterID(String PosterID) {
        commentedPosterID = PosterID;
    }
}
