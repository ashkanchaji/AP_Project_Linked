package org.Linked.client.viewControllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
        usersListView.setOnMouseClicked(this::handleListViewClick);

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
        ProfileController.setProfileUserEmail(ProfileController.getCurrentUserEmail());
        switchScenes("/fxml/profileView.fxml", searchBoxTF);
    }

    private void handleListViewClick(MouseEvent event) {
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ProfileController.setProfileUserEmail(selectedUser.getEmail());
            switchScenes("/fxml/profileView.fxml", searchBoxTF);
        }
    }
}
