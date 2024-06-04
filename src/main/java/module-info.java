module ProjectLinked {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires com.google.gson;
    requires jdk.httpserver;
    requires jjwt.api;

    opens org.Linked.client to javafx.fxml;
    opens org.Linked.client.viewControllers to javafx.fxml;

    exports org.Linked.client;
}