package org.Linked.client.viewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.Linked.server.Model.Post;

public class HomeController {

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
    private ListView<Post> postsListView;

    @FXML
    void on_addNewPostButton_clicked(ActionEvent event) {

    }

    @FXML
    void on_addPhotoButton_clicked(ActionEvent event) {

    }

    @FXML
    void on_addVideoButton_clicked(ActionEvent event) {

    }

}
