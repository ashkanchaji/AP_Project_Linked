package org.server.Controller.FxmlControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    private Button myButton;

    @FXML
    private void handleButtonAction() {
        System.out.println("Button clicked!");
    }
}
