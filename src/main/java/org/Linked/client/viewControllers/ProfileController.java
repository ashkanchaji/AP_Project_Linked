package org.Linked.client.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProfileController extends AbstractViewController {

    @FXML
    private ImageView bannerImageView;

    @FXML
    private BorderPane profileBP;

    @FXML
    private BorderPane rootBP;

    @FXML
    private VBox tabVBox;


    @FXML
    public void initialize() {
        // Load the image
//        Image image = new Image("src/main/resources/Images/banner_testi.jpg");
//        bannerImageView.setImage(image);

        // Bind ImageView's fitWidth and fitHeight properties to the BorderPane's width and height
//        tabVBox.prefWidthProperty().bind(rootBP.widthProperty());
        bannerImageView.fitWidthProperty().bind(profileBP.widthProperty());
//        bannerImageView.fitHeightProperty().bind(profileBP.heightProperty());
    }
}

