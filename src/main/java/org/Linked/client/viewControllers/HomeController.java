package org.Linked.client.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpHeaders;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.client.viewControllers.Utils.FileUtil;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class HomeController extends AbstractViewController{

    @FXML
    private Button addNewPostButton;

    @FXML
    private Button addPhotoButton;

    @FXML
    private Button addVideoButton;

    @FXML
    private TextArea newPostTextArea;

    @FXML
    private Label postLimitLabel;

    @FXML
    private GridPane postShowGridPane;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    private final String THIS_USER_EMAIL = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    private String videoFileBase64 = null;
    private String photoFileBase64 = null;

    private ArrayList<User> allUsers;
    private ArrayList<Post> allPosts;
    private ArrayList<Follow> userFollows;



    @FXML
    public void initialize() {
        postLimitLabel.setVisible(false);

        videoFileBase64 = null;
        photoFileBase64 = null;

        HttpResponse users;
        HttpResponse posts;
        HttpResponse follows;

        try {
            users = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
            posts = HttpController.sendRequest(SERVER_ADDRESS + "/posts", HttpMethod.GET, null, null);
            follows = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + THIS_USER_EMAIL, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allUsers = gson.fromJson(users.getBody(), USER_LIST_TYPE);
        allPosts = gson.fromJson(posts.getBody(), POST_LIST_TYPE);
        userFollows = gson.fromJson(follows.getBody(), FOLLOW_LIST_TYPE);

        // Reverse the list of posts
        Collections.reverse(allPosts);

        // Clear the grid pane before adding new posts
        postShowGridPane.getChildren().clear();

        int row = 1;
        for (Post post : allPosts) {
            if (shouldDisplayPost(post)) {
                row = loadPost(post, row);
            }
        }
    }


    private boolean shouldDisplayPost(Post post) {
        if (post.getUserId().equals(THIS_USER_EMAIL)) {
            return true;
        }
        for (Follow follow : userFollows) {
            if (post.getUserId().equals(follow.getFollowing())) {
                return true;
            }
        }
        return false;
    }

    private int loadPost(Post post, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PostView.fxml"));
            VBox postView = loader.load();

            PostController controller = loader.getController();

            User postUser = null;
            for (User user : allUsers) {
                if (user.getEmail().equals(post.getUserId())) {
                    postUser = user;
                    controller.initializePostData(post, postUser);
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
    void on_addNewPostButton_clicked(ActionEvent event) {
        java.sql.Date createdAt = Date.valueOf(LocalDate.now());
        Post post = null;
        try {
            post = new Post(THIS_USER_EMAIL, newPostTextArea.getText(), 0, 0, createdAt, 0);
        } catch (CharacterNumberLimitException e) {
            postLimitLabel.setVisible(true);
            return;
        }

        String postJson = gson.toJson(post);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/posts", HttpMethod.POST, postJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (videoFileBase64 != null) {

            VideoFile videoFile = new VideoFile(post.getPostId(), videoFileBase64);

            String videoJsonBody = gson.toJson(videoFile);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/videoFiles/" + post.getPostId(), HttpMethod.POST, videoJsonBody, headers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (photoFileBase64 != null) {
            PhotoFile photoFile = new PhotoFile(post.getPostId(), photoFileBase64);

            String photoJsonBody = gson.toJson(photoFile);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/photoFiles/" + post.getPostId(), HttpMethod.POST, photoJsonBody, headers);
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
        fileChooser.setTitle("Choose Photo File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg")
        ); // , "*.jpeg", "*.png", "*.gif"
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                photoFileBase64 = FileUtil.encodeFileToBase64(selectedFile.getAbsolutePath());
                videoFileBase64 = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void on_addVideoButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4")
        ); // , "*.avi", "*.mkv", "*.flv", "*.mov", "*.wmv"
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                videoFileBase64 = FileUtil.encodeFileToBase64(selectedFile.getAbsolutePath());
                photoFileBase64 = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void on_searchButton_clicked(ActionEvent event){
        switchScenes("/fxml/SearchView.fxml", searchButton);
    }

    @FXML
    void on_profileButton_clicked(ActionEvent event){
        switchScenes("/fxml/profileView.fxml", profileButton);
    }
}
