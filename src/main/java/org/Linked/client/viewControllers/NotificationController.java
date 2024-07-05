package org.Linked.client.viewControllers;

import io.github.gleidson28.GNAvatarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Model.Connect;
import org.Linked.server.Model.Follow;
import org.Linked.server.Model.Post;
import org.Linked.server.Model.User;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationController extends AbstractViewController{

    @FXML
    private GNAvatarView currAvatarPicture;

    @FXML
    private Label currUsernameLabel;

    @FXML
    private Button homeButton;

    @FXML
    private GridPane notificGridPane;

    @FXML
    private Button notificationButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button settingButton;



    private final String LOGGED_USER = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];

    private User loggedUser;

    private ArrayList<User> users;
    private ArrayList<Connect> connects;
    private ArrayList<Post> posts;

    private ArrayList<Follow> userFollows;
    private ArrayList<Connect> userConnects;

    @FXML
    public void initialize () {
        HttpResponse allUsers;
        HttpResponse allConnect;
        HttpResponse thisUser;

        try {
            allUsers = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
            thisUser = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + LOGGED_USER, HttpMethod.GET, null, null);
            allConnect = HttpController.sendRequest(SERVER_ADDRESS + "/connect", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loggedUser = gson.fromJson(thisUser.getBody(), User.class);
        users = gson.fromJson(allUsers.getBody(), USER_LIST_TYPE);
        connects = gson.fromJson(allConnect.getBody(), CONNECT_LIST_TYPE);

        Image image;
        if (loggedUser.getProfilePicture() == null || loggedUser.getProfilePicture().isEmpty()){
            image = new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString());
        } else {
            image = new Image(Paths.get(loggedUser.getProfilePicture()).toUri().toString());
        }

        currAvatarPicture.setImage(image);
        currUsernameLabel.setText(LOGGED_USER.split("@")[0]);

        Collections.reverse(connects);

        notificGridPane.getChildren().clear();

        int row = 1; // Start from the first row
        for (Connect connect : connects){
            if (connect.isPending() && connect.getReceiver().equals(LOGGED_USER)){
                row = loadRequest(connect, row);
            }
        }

        ////Show notification about following
        HttpResponse allPost;

        try {
            allPost = HttpController.sendRequest(SERVER_ADDRESS + "/posts" , HttpMethod.GET , null , null);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        posts = gson.fromJson(allPost.getBody() , POST_LIST_TYPE);

        Collections.reverse(posts);

        int row2 = 1;
        for (Post post : posts){
            if (shouldDisplayNotif(post)){
                row2 = loadMessNotif(post , row2);
            }
        }

    }

    private boolean shouldDisplayNotif(Post post) {

        HttpResponse follows;
        HttpResponse connects;

        try {
                follows = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + LOGGED_USER, HttpMethod.GET, null, null);
            connects = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + LOGGED_USER, HttpMethod.GET, null, null);
        } catch (IOException e){
            throw  new RuntimeException();
        }

        userFollows = gson.fromJson(follows.getBody(), FOLLOW_LIST_TYPE);
        userConnects = gson.fromJson(connects.getBody(), CONNECT_LIST_TYPE);

        // Check if the post is made by a user followed by the current user
        for (Follow follow : userFollows) {
            if (post.getUserId().equals(follow.getFollowing())) {
                return true;
            }
        }
        // Check if the post is made by a user connected with the current user
        for (Connect connect : userConnects) {
            if ((post.getUserId().equals(connect.getSender()) || post.getUserId().equals(connect.getReceiver())) &&
                    !connect.isPending()) {
                return true;
            }
        }
        // If none of the above conditions are met, do not display the notif
        return false;
    }

    private int loadRequest(Connect connect, int row) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectRequestView.fxml"));
            VBox requestView = loader.load();

            ConnectRequestController controller = loader.getController();

            User requestSender = null;
            for (User user : users) {
                if (user.getEmail().equals(connect.getSender())){
                    requestSender = user;
                    controller.initializeRequestData(connect, requestSender, this);
                    break;
                }
            }

            notificGridPane.add(requestView, 0, row);
            row++; // Increment the row after adding the request
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return row;
    }

    private int loadMessNotif(Post post , int row){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NotifMessage.fxml"));
            VBox messageNotif = loader.load();

            NotifMessageController controller = loader.getController();

            User notifSender = null ;
            for(User user : users){
                if (user.getEmail().equals(post.getUserId())){
                    notifSender = user;
                    controller.initializeNotifMess(notifSender);
                }
            }
            if (notifSender == null) {return row + 1;}

            notificGridPane.add(messageNotif, 0, row);
        }catch (IOException e){
            throw  new RuntimeException();
        }

        return row + 1;
    }

    @FXML
    void on_currAvatarPicture_clicked(MouseEvent event) {
        ProfileController.setProfileUserEmail(LOGGED_USER);
        switchScenes("/fxml/profileView.fxml", currAvatarPicture);
    }

    @FXML
    void on_currUsernameLabel_clicked(MouseEvent event) {
        on_currAvatarPicture_clicked(event);
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
        switchScenes("/fxml/profileView.fxml", profileButton);
    }


    @FXML
    void on_settingButton_clicked(ActionEvent event) {
        switchScenes("/fxml/LoginView.fxml", settingButton);

    }
    @FXML
    void on_logoutButton_clicked(ActionEvent event) {
        switchScenes("/fxml/LoginView.fxml" , settingButton );
    }

}
