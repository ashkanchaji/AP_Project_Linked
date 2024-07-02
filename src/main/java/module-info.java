module ProjectLinked {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires com.google.gson;
    requires jdk.httpserver;
    requires jjwt.api;
    requires com.fasterxml.jackson.databind;
    requires GNAvatarView;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.media;

    opens org.Linked.client to javafx.fxml;
    opens org.Linked.client.viewControllers to javafx.fxml;

    exports org.Linked.client;
    exports org.Linked.client.viewControllers;
}