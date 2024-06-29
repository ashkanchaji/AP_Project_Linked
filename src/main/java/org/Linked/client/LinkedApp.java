package org.Linked.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Controller.Parsers.JwtUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class LinkedApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        primaryStage.setTitle("Linked");
        primaryStage.setScene(new Scene(root, 1080, 720));
        Image icon = new Image(Paths.get("src/main/resources/Images/linkedEdited.png").toUri().toString());
        primaryStage.getIcons().add(icon);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
//        try {
//            System.out.println(JWTController.isExpired("src/main/java/org/Linked/client/Token/UserJwtToken.txt"));
//
//        } catch (Exception e) {
//            System.out.println("Error reading token: " + e.getMessage());
//        }
    }
}
