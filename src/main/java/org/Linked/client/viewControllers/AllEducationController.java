package org.Linked.client.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.server.Model.Education;

import java.io.IOException;
import java.util.ArrayList;

public class AllEducationController extends AbstractViewController{

    @FXML
    private Label activityDesSELAbel;

    @FXML
    private Label addInfoSELabel;

    @FXML
    private Label gradeSELabel;

    @FXML
    private Label graduationSELabel;

    @FXML
    private Label instituteNameSELabel;

    @FXML
    private Label majorSELabel;

    @FXML
    private Label registrationSELabel;


    public  void initializeEdu (Education edu){

        instituteNameSELabel.setText(edu.getCollegeName());
        majorSELabel.setText(edu.getMajor());
        gradeSELabel.setText(edu.getGrade());
        activityDesSELAbel.setText(edu.getActivitiesInfo());
        addInfoSELabel.setText(edu.getAdditionalInfo());
        registrationSELabel.setText(edu.getEnterYear().toString());
        graduationSELabel.setText(edu.getExitYear().toString());

    }

}

