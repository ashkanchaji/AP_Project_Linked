package org.Linked.client.FXModels;

import io.github.gleidson28.GNAvatarView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.Linked.client.Model.Post;

public class PostCell extends ListCell<Post> {
    private VBox baseVbox = new VBox(8.0);

    private HBox profileHbox = new HBox(8.0);
    private GNAvatarView avatarView = new GNAvatarView();
    private Label userNameLabel = new Label();
    private Label fullNameLabel = new Label();

    private TextArea postText = new TextArea();
    private MediaView mediaView = new MediaView();
    private HBox postActionsHbox = new HBox(8.0);
    private Button likeButton = new Button("Like");
    private Button commentButton = new Button("Comment");
    private Button showCommentsButton = new Button("Show Comments");
}
