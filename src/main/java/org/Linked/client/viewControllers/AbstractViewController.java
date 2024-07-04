package org.Linked.client.viewControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.Linked.client.viewControllers.Utils.*;
import org.Linked.server.Model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class AbstractViewController {
    private final static String PORT = "8080";
    protected final static String SERVER_ADDRESS = "http://127.0.0.1:" + PORT;

    protected double previousSceneWidth;
    protected double previousSceneHeight;

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserTypeAdapter())
            .registerTypeAdapter(Follow.class, new FollowTypeAdapter())
            .registerTypeAdapter(Education.class , new EducationTypeAdapter())
            .registerTypeAdapter(EducationSkills.class, new EducationSkillsTypeAdapter())
            .registerTypeAdapter(Post.class, new PostTypeAdapter())
            .registerTypeAdapter(Comment.class, new CommentTypeAdapter())
            .registerTypeAdapter(ContactsInfo.class, new ContactsInfoTypeAdapter())
            .registerTypeAdapter(Connect.class , new ConnectTypeAdapter())
            .registerTypeAdapter(VideoFile.class, new VideoFileTypeAdapter())
            .registerTypeAdapter(PhotoFile.class, new PhotoFileTypeAdapter())
            .registerTypeAdapter(Like.class, new LikeTypeAdapter())
            .create();
    protected static final Type USER_LIST_TYPE = new TypeToken<ArrayList<User>>() {}.getType();
    protected static final Type FOLLOW_LIST_TYPE = new TypeToken<ArrayList<Follow>>() {}.getType();
    protected static final Type SKILL_LIST_TYPE = new TypeToken<ArrayList<String>>() {}.getType();
    protected static final Type POST_LIST_TYPE = new TypeToken<ArrayList<Post>>() {}.getType();
    protected static final Type COMMENT_LIST_TYPE = new TypeToken<ArrayList<Comment>>() {}.getType();
    protected static final Type CONNECT_LIST_TYPE = new TypeToken<ArrayList<Connect>>() {}.getType();
    protected static final Type LIKE_LIST_TYPE = new TypeToken<ArrayList<Like>>() {}.getType();
    protected static final Type EDUCATION_LIST_TYPE = new TypeToken<ArrayList<Education>>() {}.getType();


    protected <T extends Node> void switchScenes (String path, T lastSceneNode) {
        try {
            // Store the current scene dimensions
            Stage currentStage = (Stage) lastSceneNode.getScene().getWindow();
            previousSceneWidth = currentStage.getWidth();
            previousSceneHeight = currentStage.getHeight();

            // Load the FXML file for the SignUp page
            Parent profilePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));

            // Create a new scene using the SignUp page
            Scene profileScene = new Scene(profilePage);

            // Set the scene size to match the previous scene size
            profileScene.setRoot(profilePage);
            currentStage.setScene(profileScene);

            // Apply the previous scene size
            currentStage.setWidth(previousSceneWidth);
            currentStage.setHeight(previousSceneHeight);

            // Alternatively, maximize the stage if needed
            // currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
