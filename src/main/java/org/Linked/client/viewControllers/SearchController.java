package org.Linked.client.viewControllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Linked.client.Models.ProfileSearchCell;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.server.Model.User;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController extends AbstractViewController{

    @FXML
    private Button homeButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField searchBoxTF;

    @FXML
    private Button searchButton;

    @FXML
    private Button searchUserButton;

    @FXML
    private Button settingButton;

    @FXML
    private ListView<User> usersListView;

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private ArrayList<User> allUsers;

    @FXML
    public void initialize() {
        usersListView.setItems(users);

        usersListView.setCellFactory(
                new Callback<ListView<User>, ListCell<User>>() {
                    @Override
                    public ListCell<User> call(ListView<User> param) {
                        return new ProfileSearchCell();
                    }
                }
        );

        // Bind the ListView's height to the total height of its cells
        usersListView.prefHeightProperty().bind(Bindings.size(users).multiply(80));

        HttpResponse allUsersResponse;

        try {
            allUsersResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (allUsersResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        allUsers = gson.fromJson(allUsersResponse.getBody(), USER_LIST_TYPE);
    }

    @FXML
    void on_searchUserButton_clicked(ActionEvent event) {
        // Clear the existing items in the list
        users.clear();

        // Iterate through all users to find matches
        for (User user : allUsers) {
            String fullName = user.getFirstName() + " " +
                    (user.getAdditionalName() == null ? "" : (user.getAdditionalName() + " ")) +
                    user.getLastName();

            // Check if the search term is in the user's email or full name
            if (user.getEmail().toLowerCase().contains(searchBoxTF.getText().toLowerCase()) ||
                    fullName.toLowerCase().contains(searchBoxTF.getText().toLowerCase())) {
                users.add(user);
            }
        }

        // Set the updated list to the ListView (this step may be redundant if 'users' is already bound)
        usersListView.setItems(users);
    }


    @FXML
    void on_profileButton_clicked(ActionEvent event) {
        try {
            // Store the current scene dimensions
            Stage currentStage = (Stage) searchBoxTF.getScene().getWindow();
            previousSceneWidth = currentStage.getWidth();
            previousSceneHeight = currentStage.getHeight();

            // Load the FXML file for the SignUp page
            Parent profilePage = FXMLLoader.load(getClass().getResource("/fxml/profileView.fxml"));

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
