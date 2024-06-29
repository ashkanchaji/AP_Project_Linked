package org.Linked.client.Models;

import io.github.gleidson28.GNAvatarView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import org.Linked.server.Model.User;

import java.nio.file.Paths;

public class ProfileSearchCell extends ListCell<User> {
    private HBox hBox = new HBox(8.0);
    private GNAvatarView avatarView = new GNAvatarView();
    private Label userNameLabel = new Label();
    private Label fullNameLabel = new Label();

    public ProfileSearchCell() {
        hBox.setAlignment(Pos.CENTER_LEFT);

        avatarView.setPrefHeight(80.0);
        avatarView.setPrefWidth(80.0);

        hBox.getChildren().add(avatarView);

        userNameLabel.setTextAlignment(TextAlignment.LEFT);
        fullNameLabel.setTextAlignment(TextAlignment.LEFT);

        userNameLabel.getStyleClass().add("email-label"); // Add style class
        fullNameLabel.getStyleClass().add("name-label"); // Add style class

        hBox.getChildren().add(userNameLabel);
        hBox.getChildren().add(fullNameLabel);

        hBox.getStyleClass().add("list-cell"); // Add style class to the HBox

        setPrefSize(80.0, 80.0);
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            if (item.getProfilePicture() != null && !item.getProfilePicture().isEmpty()){
                avatarView.setImage(new Image(Paths.get(item.getProfilePicture()).toUri().toString()));
            } else {
                avatarView.setImage(new Image(Paths.get("src/main/resources/Images/default_profile_image.jpeg").toUri().toString()));
            }
            String userName = item.getEmail().split("@")[0];
            userNameLabel.setText(userName);
            fullNameLabel.setText(item.getFirstName() + " " +
                    (item.getAdditionalName() == null ? "" : (item.getAdditionalName() + " ")) +
                    item.getLastName());

            setGraphic(hBox);
        }
    }
}
