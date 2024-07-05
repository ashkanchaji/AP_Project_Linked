package org.Linked.client.viewControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gleidson28.GNAvatarView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Linked.client.FXModels.ProfileSearchCell;
import org.Linked.client.viewControllers.Http.HttpController;
import org.Linked.client.viewControllers.Http.HttpMethod;
import org.Linked.client.viewControllers.Http.HttpResponse;
import org.Linked.client.viewControllers.Utils.JWTController;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ProfileController extends AbstractViewController{

    @FXML
    private Label GradeEduLabel;

    @FXML
    private Label GraduationDateEduLabel;

    @FXML
    private RadioButton MobileNumberContactsRB;

    @FXML
    private Label RegisterDateEduLabel;

    @FXML
    private Label activityDescriptionEduLabel;

    @FXML
    private TextArea activityEduTF;

    @FXML
    private Label addInfoEduLabel;

    @FXML
    private TextArea additionalEduTF;

    @FXML
    private TextField additionalNameEditTF;

    @FXML
    private Label addressContactsLabel;

    @FXML
    private TextArea addressContactsTF;

    @FXML
    private ImageView bannerImageView;

    @FXML
    private DatePicker birthdayContactsDP;

    @FXML
    private Label birthdayContactsLabel;

    @FXML
    private Button cancelContactsButton;

    @FXML
    private Button cancelEduButton;

    @FXML
    private Button cancelInfoButton;

    @FXML
    private Button cancelSkillsButton;

    @FXML
    private Button chooseAvatarButton;

    @FXML
    private Button chooseBannerButton;

    @FXML
    private Label cityAndCountryLabel;

    @FXML
    private TextField cityEditTF;


    @FXML
    private Label connectionCountLabel;

    @FXML
    private VBox contactsInfoVbox;

    @FXML
    private RadioButton contactsOnlyBirthContactsRB;

    @FXML
    private TextField countryEditTF;

    @FXML
    private Button editContactButton;

    @FXML
    private Button editEduButton;

    @FXML
    private Button editInfoButton;

    @FXML
    private VBox editInfoVbox;

    @FXML
    private Button editSkillsButton;

    @FXML
    private Label educationLabel1;

    @FXML
    private Label educationLabel11;

    @FXML
    private VBox educationVbox;

    @FXML
    private Label emailContactsLabel;

    @FXML
    private TextField emailContactsTF;

    @FXML
    private RadioButton everyoneBirthContactsRB;

    @FXML
    private Button exitFollowShowButton;

    @FXML
    private TextField firstNameEditTF;

    @FXML
    private Button followButton;

    @FXML
    private Label followersCountLabel;

    @FXML
    private Label followersFollowShowLbl;

    @FXML
    private Label followingsCountLabel;

    @FXML
    private Label followingsFollowshowLbl;

    @FXML
    private Label fullNameLabel;

    @FXML
    private TextField gradeEduTF;

    @FXML
    private DatePicker graduateDateDP;

    @FXML
    private TextArea headLineTA;

    @FXML
    private TextArea headlineEditTA;

    @FXML
    private RadioButton hiringRadioBtn;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHbox;

    @FXML
    private RadioButton homeNumberContactsRB;

    @FXML
    private Label instituteNameEduLabel;

    @FXML
    private TextField instituteNameEduTF;

    @FXML
    private RadioButton jobRadioBtn;

    @FXML
    private TextField lastNameEditTF;

    @FXML
    private Label majorEduLabel;

    @FXML
    private TextField majorEduTF;

    @FXML
    private RadioButton nobodyBirthContactsRB;

    @FXML
    private Button notificationButton;

    @FXML
    private HBox notificationHbox;

    @FXML
    private Label numberContactsLabel;

    @FXML
    private TextField numberContactsTF;

    @FXML
    private ToggleGroup numberFormat;

    @FXML
    private Label otherAccContactsLabel;

    @FXML
    private TextArea otherAccountsContactsTF;

    @FXML
    private TextField professionEditTF;

    @FXML
    private Label professionLabel;

    @FXML
    private GNAvatarView profileAvatar;

    @FXML
    private BorderPane profileBP;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHbox;

    @FXML
    private StackPane profileStackPane;

    @FXML
    private DatePicker registerDateDP;

    @FXML
    private BorderPane rootBP;

    @FXML
    private Button saveContactsButton;

    @FXML
    private Button saveEduButton;

    @FXML
    private Button saveInfoButton;

    @FXML
    private Button saveSkillsButton;

    @FXML
    private Button searchButton;

    @FXML
    private HBox searchHbox;

    @FXML
    private RadioButton servicesRadioBtn;

    @FXML
    private Button settingButton;

    @FXML
    private HBox settingHbox;

    @FXML
    private Label skill1SkillsLabel;

    @FXML
    private Label skill1SkillsLabel1;

    @FXML
    private Label skill1SkillsLabel2;

    @FXML
    private Label skill1SkillsLabel3;

    @FXML
    private Label skill1SkillsLabel4;

    @FXML
    private TextField skill1SkillsTF;

    @FXML
    private TextField skill2SkillsTF;

    @FXML
    private TextField skill3SkillsTF;

    @FXML
    private TextField skill4SkillsTF;

    @FXML
    private TextField skill5SkillsTF;

    @FXML
    private VBox skillsVbox;

    @FXML
    private VBox tabVBox;

    @FXML
    private ListView<User> usersListView;

    @FXML
    private RadioButton workNumberContactsRB;

    @FXML
    private VBox followShowVbox;

    @FXML
    private final ToggleGroup workStatus = new ToggleGroup();

    @FXML
    private Label educationLimitLabel;

    @FXML
    private Label contactLimitLabel;

    @FXML
    private Label workStatusLabel;

    @FXML
    private Button closeConnectButton;

    @FXML
    private Button connectButton;

    @FXML
    private TextArea connectReqTA;

    @FXML
    private VBox connectVbox;

    @FXML
    private Button sendConnectButton;


    @FXML
    private Button closeConnectionListViewButton;

    @FXML
    private ListView<User> connectionListView;

    @FXML
    private VBox connectionListViewVbox;

    @FXML
    private Label showAllEduLabel;


    @FXML
    private VBox showAllEduVbox;


    @FXML
    private GridPane showAllEduGridpane;

    @FXML
    private Button addEduButton;

    @FXML
    private Button addEduVboxButton;

    @FXML
    private VBox addEduVbox;

    @FXML
    private TextField majorEduAddTF;

    @FXML
    private TextField instituteEduAddTF;

    @FXML
    private TextArea additionalEduAddTA;

    @FXML
    private Button cancelEduAddButton;

    @FXML
    private TextField gradeEduAddTF;

    @FXML
    private TextArea activityEduAddTA;

    @FXML
    private DatePicker registerDateAddDP;

    @FXML
    private DatePicker graduateDateAddDP;

    @FXML
    private RadioButton contactsNetworkContacts;

    @FXML
    private Button messageButton;

    private ToggleGroup birthDayToggle = new ToggleGroup();


    ////////////////////////////////// ___ follow/ connect listView fields ___ /////////////////////////////////////////
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private ArrayList<User> allUsers;
    private ArrayList<Follow> follows;

    private int followingCount = 0;
    private int followersCount = 0;

    private ArrayList<Connect> connects;

    private int connectionsCount =0;


    ///////////////////////////////////////// ___ profile/user emails ___ //////////////////////////////////////////////
    private String avatarAddress;
    private String bannerAddress;
    private static String currentUserEmail = JWTController.getSubjectFromJwt(JWTController.getJwtKey()).split(":")[0];
    private static String profileUserEmail = currentUserEmail;

    ///////////////////////////////////////// ___ general info fields ___ //////////////////////////////////////////////
    private String firstName;
    private String lastName;
    private String password;
    private JsonNode additionalName;
    private JsonNode headline;
    private JsonNode country;
    private JsonNode city;
    private JsonNode profession;

    ///////////////////////////////////////// ___ education fields ___ /////////////////////////////////////////////////
    private JsonNode instituteName;
    private JsonNode major;
    private JsonNode registerDate;
    private JsonNode graduationDate;
    private JsonNode grade;
    private JsonNode activityDiscription;
    private JsonNode additionalInformation;

    private JsonNode skills;


    ///////////////////////////////////////// ___ contacts fields ___ //////////////////////////////////////////////////

    private JsonNode email;
    private JsonNode phoneNumber;
    private JsonNode phoneType;
    private JsonNode address;
    private JsonNode birthday;
    private JsonNode birthdayVisibility;
    private JsonNode otherAccounts;

    /////////////////////////////////////////// ___ Skills fields ___ //////////////////////////////////////////////////

    private JsonNode eduSkill1;
    private JsonNode eduSkill2;
    private JsonNode eduSkill3;
    private JsonNode eduSkill4;
    private JsonNode eduSkill5;

    //////////////////////////////////////////// ___ initialize ___ ////////////////////////////////////////////////////

    @FXML
    public void initialize() {
        bannerImageView.fitWidthProperty().bind(profileBP.widthProperty());
        jobRadioBtn.setToggleGroup(workStatus);
        hiringRadioBtn.setToggleGroup(workStatus);
        servicesRadioBtn.setToggleGroup(workStatus);

        everyoneBirthContactsRB.setToggleGroup(birthDayToggle);
        contactsNetworkContacts.setToggleGroup(birthDayToggle);
        contactsOnlyBirthContactsRB.setToggleGroup(birthDayToggle);
        nobodyBirthContactsRB.setToggleGroup(birthDayToggle);

        editInfoVbox.setVisible(false);
        editInfoVbox.setDisable(true);
        followShowVbox.setVisible(false);
        connectVbox.setVisible(false);

        followersCount = calculateFollowers();
        followingCount = calculateFollowings();

        followingsCountLabel.setText(followingCount + " Followings");
        followersCountLabel.setText(followersCount + " Followers");

        connectionCountLabel.setText(calculateConnections() + " Connections");

        /* ___ GET USER INFO ___ */

        HttpResponse userResponse = getUserResponse();

        JsonNode userJson = getUserJson(userResponse);

        firstName = userJson.get("firstName").asText();
        lastName = userJson.get("lastName").asText();
        password = userJson.get("password").asText();
        additionalName = userJson.get("additionalName");
        headline = userJson.get("headline");
        country = userJson.get("country");
        String countryName = country == null ? "" : country.asText();
        city = userJson.get("city");
        String cityName = city == null ? "" : city.asText();
        profession = userJson.get("profession");

        firstNameEditTF.setText(firstName);
        additionalNameEditTF.setText(additionalName == null ? "" : additionalName.asText());
        lastNameEditTF.setText(lastName);
        headlineEditTA.setText(headline == null ? "" : headline.asText());
        countryEditTF.setText(countryName);
        cityEditTF.setText(cityName);
        professionEditTF.setText(profession == null ? "" : profession.asText().split("::")[0]);

        avatarAddress = userJson.get("profilePicture") == null ? null : userJson.get("profilePicture").asText();
        bannerAddress = userJson.get("backgroundPicture") == null ? null : userJson.get("backgroundPicture").asText();

        if (avatarAddress != null){
            Image avatar = new Image(Paths.get(avatarAddress).toUri().toString());
            profileAvatar.setImage(avatar);
        }

        if (bannerAddress != null){
            Image banner = new Image(Paths.get(bannerAddress).toUri().toString());
            bannerImageView.setImage(banner);
        }

        fullNameLabel.setText(firstName + " " + (additionalName == null ? "" : (additionalName.asText() + " ")) + lastName);
        headLineTA.setText(headline == null ? "No Headline." : headline.asText());
        professionLabel.setText(profession == null ? "No Profession" : profession.asText().split("::")[0]);
        cityAndCountryLabel.setText(cityName + ", " + countryName);
        try {
            workStatusLabel.setText(profession == null ? "" : profession.asText().split("::")[1]);
            for (Toggle toggle : workStatus.getToggles()){
                if (((RadioButton) toggle).getText().equals(profession == null ? "" : profession.asText().split("::")[1])){
                    workStatus.selectToggle(toggle);
                    break;
                } else {
                    workStatus.selectToggle(null);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            workStatusLabel.setText("");
            workStatus.selectToggle(null);
        }


        //education
        HttpResponse educationResponse = getEducationResponse();

        JsonNode educationJson = getEducationJson(educationResponse);

        if (educationJson != null) {
            instituteName = educationJson.get("collegeName");
            major = educationJson.get("major");
            registerDate = educationJson.get("enterYear");
            graduationDate = educationJson.get("exitYear");
            grade = educationJson.get("grade");
            activityDiscription = educationJson.get("activitiesInfo");
            additionalInformation = educationJson.get("additionalInfo");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            instituteNameEduTF.setText(instituteName == null ? "" : instituteName.asText());
            majorEduTF.setText(major == null ? "" : major.asText());

            try {
                if (registerDate != null) {
                    LocalDate registerLocalDate = LocalDate.parse(registerDate.asText(), dateTimeFormatter);
                    registerDateDP.setValue(registerDate == null ? null: registerLocalDate);
                }
                if (graduationDate != null) {
                    LocalDate graduateLocalDate = LocalDate.parse(graduationDate.asText(), dateTimeFormatter);
                    graduateDateDP.setValue(registerDate == null ? null: graduateLocalDate);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid register date format: " + registerDate);
                // Handle the exception (e.g., show an error message)
            }

            gradeEduTF.setText(grade == null ? "" : grade.asText());
            activityEduTF.setText(activityDiscription == null ? "" : activityDiscription.asText());
            additionalEduTF.setText(additionalInformation == null ? "" : additionalInformation.asText());

            instituteNameEduLabel.setText(instituteName == null ? "Institute Name: -" : "Institute Name: " + instituteName.asText());
            majorEduLabel.setText(major == null ? "Major: -" : "Major: " + major.asText());
            RegisterDateEduLabel.setText(registerDate == null ? "Registration date: -" : "Registration date: " + registerDate.asText());
            GraduationDateEduLabel.setText(graduationDate == null ? "Graduations date: -" : "Graduations date: " + graduationDate.asText());
            GradeEduLabel.setText(grade == null ? "Grade: -" : "Grade: " + grade.asText());
            activityDescriptionEduLabel.setText(activityDiscription == null ? "Activity description: -" : "Activity description: " + activityDiscription.asText());
            addInfoEduLabel.setText(additionalInformation == null ? "Additional info: -" : "Additional info: " + additionalInformation.asText());

        }

        // contacts info
        HttpResponse contactsResponse = getContactsInfoResponse();

        JsonNode contactsJson = getContactsJson(contactsResponse);

        if (contactsJson != null) {
            email = contactsJson.get("contactEmail");
            phoneNumber = contactsJson.get("phoneNumber");
            phoneType = contactsJson.get("phoneType");
            address = contactsJson.get("address");
            birthday = contactsJson.get("birthday");
            birthdayVisibility = contactsJson.get("birthdayVisibility");
            otherAccounts = contactsJson.get("contactUs");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            emailContactsTF.setText(email == null ? "" : email.asText());
            numberContactsTF.setText(phoneNumber == null ? "" : phoneNumber.asText());
            addressContactsTF.setText(address == null ? "" : address.asText());
            try {
                if (birthday != null){
                    LocalDate birthDayLocalDate = LocalDate.parse(birthday.asText(), dateTimeFormatter);
                    birthdayContactsDP.setValue(birthDayLocalDate);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid register date format: " + registerDate);
                // Handle the exception (e.g., show an error message)
            }
            otherAccountsContactsTF.setText(otherAccounts == null ? "" : otherAccounts.asText());

            if (phoneType != null) {
                for (Toggle toggle : numberFormat.getToggles()){
                    if (((RadioButton) toggle).getText().equals(phoneType.asText())){
                        numberFormat.selectToggle(toggle);
                        break;
                    }
                }
            }

            String birthDayVisibility = null;

            if (birthdayVisibility != null) {
                for (Toggle toggle : birthDayToggle.getToggles()){
                    if (((RadioButton) toggle).getText().equals(birthdayVisibility.asText())){
                        birthDayToggle.selectToggle(toggle);
                        birthDayVisibility = birthdayVisibility.asText();
                        break;
                    }
                }
            }

            String selectedButtonName = phoneType == null ? "" : "(" + phoneType.asText() + ") ";

            emailContactsLabel.setText(email == null ? "Email: -" : "Email: " + email.asText());
            numberContactsLabel.setText(phoneNumber == null ? "Number: -" : "Number: " + selectedButtonName + phoneNumber.asText());
            addressContactsLabel.setText(address == null ? "Address: -" : "Address: " + address.asText());
            checkBirthdayShow(birthDayVisibility, birthday);
            otherAccContactsLabel.setText(otherAccounts == null ? "Other accounts: -" : "Other accounts: " + otherAccounts.asText());
        }

        // skills

        HttpResponse eduSkillsResponse = getEducationSkillsResponse();

        JsonNode eduSkillsJson = getEducatinSkillsJson(eduSkillsResponse);

        if (eduSkillsJson != null) {
            eduSkill1 = eduSkillsJson.get("skill1");
            eduSkill2 = eduSkillsJson.get("skill2");
            eduSkill3 = eduSkillsJson.get("skill3");
            eduSkill4 = eduSkillsJson.get("skill4");
            eduSkill5 = eduSkillsJson.get("skill5");

            skill1SkillsTF.setText(eduSkill1 == null ? "" : eduSkill1.asText());
            skill2SkillsTF.setText(eduSkill2 == null ? "" : eduSkill2.asText());
            skill3SkillsTF.setText(eduSkill3 == null ? "" : eduSkill3.asText());
            skill4SkillsTF.setText(eduSkill4 == null ? "" : eduSkill4.asText());
            skill5SkillsTF.setText(eduSkill5 == null ? "" : eduSkill5.asText());

            skill1SkillsLabel.setText(eduSkill1 == null ? "" : "Skill 1: " + eduSkill1.asText());
            skill1SkillsLabel1.setText(eduSkill2 == null ? "" : "Skill 2: " + eduSkill2.asText());
            skill1SkillsLabel2.setText(eduSkill3 == null ? "" : "Skill 3: " + eduSkill3.asText());
            skill1SkillsLabel3.setText(eduSkill4 == null ? "" : "Skill 4: " + eduSkill4.asText());
            skill1SkillsLabel4.setText(eduSkill5 == null ? "" : "Skill 5: " + eduSkill5.asText());
        }

        if (profileUserEmail.equals(currentUserEmail)) {
            followButton.setVisible(false);
            connectButton.setVisible(false);
            messageButton.setVisible(false);
        } else {
            editInfoButton.setVisible(false);
            editContactButton.setVisible(false);
            editEduButton.setVisible(false);
            editSkillsButton.setVisible(false);
            messageButton.setVisible(false);

            try {
                HttpResponse httpResponse = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + currentUserEmail +
                        "/" + profileUserEmail, HttpMethod.GET, null, null);
                if (!httpResponse.getBody().equals("No such follow info found!") ) {
                    followButton.setText("Following");
                } else {
                    followButton.setText("Follow");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                HttpResponse connectResponse = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + currentUserEmail +
                        "/" + profileUserEmail, HttpMethod.GET, null, null);
                if (!connectResponse.getBody().equals("No such connect info found!")) {
                    Connect connect = gson.fromJson(connectResponse.getBody(), Connect.class);
                    if (connect.isPending()){
                        connectButton.setText("Requested");
                    } else {
                        connectButton.setText("Connected");
                        messageButton.setVisible(true);
                    }
                } else {
                    HttpResponse connectResponse2 = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + profileUserEmail +
                            "/" + currentUserEmail, HttpMethod.GET, null, null);
                    if (!connectResponse2.getBody().equals("No such connect info found!")){
                        Connect connect = gson.fromJson(connectResponse2.getBody(), Connect.class);
                        if (connect.isPending()){
                            connectButton.setText("Pending");
                        } else {
                            connectButton.setText("Connected");
                            messageButton.setVisible(true);
                        }
                    } else {
                        connectButton.setText("Connect");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkBirthdayShow(String birthDayVisibility, JsonNode birthday) {
        if (birthDayVisibility == null || birthDayVisibility.equals("Everyone")) {
            birthdayContactsLabel.setText(birthday == null ? "Birthday: -" : "Birthday: " + birthday.asText());
        } else if (birthDayVisibility.equals("Contact Network")) {
            try {
                // Fetch first level connections
                Set<String> firstLevelConnections = fetchConnections(profileUserEmail);

                // Fetch second level connections
                Set<String> secondLevelConnections = new HashSet<>();
                for (String connection : firstLevelConnections) {
                    secondLevelConnections.addAll(fetchConnections(connection));
                }

                // Fetch third level connections
                Set<String> thirdLevelConnections = new HashSet<>();
                for (String connection : secondLevelConnections) {
                    thirdLevelConnections.addAll(fetchConnections(connection));
                }

                // Combine all levels of connections
                Set<String> allConnections = new HashSet<>(firstLevelConnections);
                allConnections.addAll(secondLevelConnections);
                allConnections.addAll(thirdLevelConnections);

                // Check if the current user is within third-level connections
                if (allConnections.contains(currentUserEmail)) {
                    birthdayContactsLabel.setText(birthday == null ? "Birthday: -" : "Birthday: " + birthday.asText());
                } else {
                    birthdayContactsLabel.setText("Birthday: -");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (birthDayVisibility.equals("Contacts Only")) {
            try {
                ArrayList<Connect> connects = fetchDirectConnections(profileUserEmail);

                for (Connect connect : connects) {
                    if ((connect.getSender().equals(profileUserEmail) || connect.getReceiver().equals(profileUserEmail)) && !connect.isPending()) {
                        birthdayContactsLabel.setText(birthday == null ? "Birthday: -" : "Birthday: " + birthday.asText());
                        return;
                    }
                }
                birthdayContactsLabel.setText("Birthday: -");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (profileUserEmail.equals(currentUserEmail)) {
                birthdayContactsLabel.setText(birthday == null ? "Birthday: -" : "Birthday: " + birthday.asText());
            } else {
                birthdayContactsLabel.setText("Birthday: -");
            }
        }
    }

    private Set<String> fetchConnections(String email) throws IOException {
        HttpResponse connectsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + email, HttpMethod.GET, null, null);
        ArrayList<Connect> connects = gson.fromJson(connectsResponse.getBody(), CONNECT_LIST_TYPE);

        Set<String> connections = new HashSet<>();
        for (Connect connect : connects) {
            if (!connect.isPending()) {
                if (connect.getSender().equals(email)) {
                    connections.add(connect.getReceiver());
                } else {
                    connections.add(connect.getSender());
                }
            }
        }
        return connections;
    }

    private ArrayList<Connect> fetchDirectConnections(String email) throws IOException {
        HttpResponse connectsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + email, HttpMethod.GET, null, null);
        return gson.fromJson(connectsResponse.getBody(), CONNECT_LIST_TYPE);
    }


    ////////////////////////////////////////// ___ left bar buttons ___ ////////////////////////////////////////////////

    @FXML
    void on_searchButton_clicked(ActionEvent event) {
        switchScenes("/fxml/searchView.fxml", searchButton);
    }

    @FXML
    void on_homeButton_clicked(ActionEvent event) {
        switchScenes("/fxml/HomeView.fxml", homeButton);
    }

    @FXML
    void on_notificationButton_clicked(ActionEvent event){
        switchScenes("/fxml/NotificationView.fxml", notificationButton);
    }
    @FXML
    void on_logoutButton_clicked(ActionEvent event) {
        switchScenes("/fxml/LoginView.fxml" , settingButton );
    }

    @FXML
    void on_profileButton_clicked(ActionEvent event){
        switchScenes("/fxml/profileView.fxml", profileButton);
    }



    ////////////////////////////////////////// ___ profile general info ___ ////////////////////////////////////////////

    @FXML
    void on_cancelInfoButton_clicked(ActionEvent event) {
        editInfoVbox.setVisible(false);
        editInfoVbox.setDisable(true);
    }

    @FXML
    void on_chooseAvatarButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        avatarAddress = selectedFile == null ? null : selectedFile.getAbsolutePath();
    }

    @FXML
    void on_chooseBannerButton_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        bannerAddress = selectedFile == null ? null : selectedFile.getAbsolutePath();
    }

    @FXML
    void on_editInfoButton_clicked(ActionEvent event) {
        editInfoVbox.setDisable(false);
        editInfoVbox.setVisible(true);
    }

    @FXML
    void on_saveInfoButton_clicked(ActionEvent event) {
        String newAdditionalName = additionalNameEditTF.getText();
        String newHeadline = headlineEditTA.getText();
        String newCountry = countryEditTF.getText();
        String newCity = cityEditTF.getText();
        RadioButton selectedWorkStatus = (RadioButton) workStatus.getSelectedToggle();
        String newProfession = professionEditTF.getText() + "::" + (selectedWorkStatus == null ? "" : selectedWorkStatus.getText());
        User user = new User(currentUserEmail, firstNameEditTF.getText(), lastNameEditTF.getText(), password,
                newAdditionalName, avatarAddress, bannerAddress, newHeadline, newCountry, newCity, newProfession,
                JWTController.getJwtKey());


        String userJson = gson.toJson(user);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.PUT, userJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        editInfoVbox.setDisable(true);
        editInfoVbox.setVisible(false);
        initialize();
    }

    /////////////////////////////////////////////// ___ message ___ ////////////////////////////////////////////////////

    @FXML
    void on_messageButton_clicked(ActionEvent event) {
        DMController.setDMedUser(profileUserEmail);
        switchScenes("/fxml/DMView.fxml", messageButton);
    }

    //////////////////////////////////////////////// ___ follow ___ ////////////////////////////////////////////////////

    @FXML
    void on_followButton_clicked(ActionEvent event) {
        if (followButton.getText().equals("Follow")){
            Follow follow = new Follow(currentUserEmail, profileUserEmail);
            String followJson = gson.toJson(follow);

            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + currentUserEmail, HttpMethod.POST, followJson, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            followButton.setText("Following");
        } else {
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + currentUserEmail + "/" + profileUserEmail,
                        HttpMethod.DELETE, null, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            followButton.setText("Follow");
        }
        initialize();
    }
    
    @FXML
    void on_followersCountLabel_clicked(MouseEvent event) {
        initializeFollowListView(true);

        HttpResponse followsResponse;

        try {
            followsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/follow", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (followsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        follows = gson.fromJson(followsResponse.getBody(), FOLLOW_LIST_TYPE);

        for (User user : allUsers) {
            for (Follow follow : follows) {
                if (follow.getFollowing().equals(profileUserEmail)) {
                    if (user.getEmail().equals(follow.getFollower())){
                        users.add(user);
                    }
                }
            }
        }

        followersFollowShowLbl.setStyle("-fx-underline: true;");
        followingsFollowshowLbl.setStyle("-fx-underline: false;");
        usersListView.setItems(users);
    }

    @FXML
    void on_followingsCountLabel_clicked(MouseEvent event) {
        initializeFollowListView(true);

        HttpResponse followsResponse;

        try {
            followsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (followsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        follows = gson.fromJson(followsResponse.getBody(), FOLLOW_LIST_TYPE);

        for (User user : allUsers) {
            for (Follow follow : follows) {
                if (user.getEmail().equals(follow.getFollowing())){
                    users.add(user);
                }
            }
        }

        followersFollowShowLbl.setStyle("-fx-underline: false;");
        followingsFollowshowLbl.setStyle("-fx-underline: true;");

        usersListView.setItems(users);
    }

    private void initializeFollowListView(boolean visibility) {
        users.clear();
        if (follows != null) follows.clear();

        followShowVbox.setVisible(visibility);

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

    private int calculateFollowers() {
        initializeFollowListView(false);

        HttpResponse followsResponse;

        try {
            followsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/follow", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (followsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        follows = gson.fromJson(followsResponse.getBody(), FOLLOW_LIST_TYPE);

        for (User user : allUsers) {
            for (Follow follow : follows) {
                if (follow.getFollowing().equals(profileUserEmail)) {
                    if (user.getEmail().equals(follow.getFollower())){
                        users.add(user);
                    }
                }
            }
        }

        return users.size();
    }


    private int calculateFollowings() {
        initializeFollowListView(false);

        HttpResponse followsResponse;

        try {
            followsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/follow/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (followsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        follows = gson.fromJson(followsResponse.getBody(), FOLLOW_LIST_TYPE);

        for (User user : allUsers) {
            for (Follow follow : follows) {
                if (user.getEmail().equals(follow.getFollowing())){
                    users.add(user);
                }
            }
        }

        return users.size();
    }

    @FXML
    void on_exitFollowShowButton_clicked(ActionEvent event) {
        followShowVbox.setVisible(false);
    }

    @FXML
    void on_followersFollowShowLbl_clicked(MouseEvent event) {
        on_followersCountLabel_clicked(event);
    }

    @FXML
    void on_followingsFollowshowLbl_clicked(MouseEvent event) {
        on_followingsCountLabel_clicked(event);
    }

    private void handleListViewClick(MouseEvent event) {
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ProfileController.setProfileUserEmail(selectedUser.getEmail());
            switchScenes("/fxml/profileView.fxml", followShowVbox);
        }
    }

    ////////////////////////////////////////////// ___ connect ___ /////////////////////////////////////////////////////

    @FXML
    void on_sendConnectButton_clicked(ActionEvent event) {
        String note = connectReqTA.getText();
        Connect connect = new Connect(currentUserEmail, profileUserEmail , note, true);
        String connectJson = gson.toJson(connect);

        try {
            HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + currentUserEmail, HttpMethod.POST, connectJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connectButton.setText("Requested");

        initialize();
    }
    @FXML
    void on_closeConnectButton_clicked(ActionEvent event) {
        connectVbox.setVisible(false);

    }

    @FXML
    void on_connectButton_clicked(ActionEvent event) {
        if (connectButton.getText().equals("Connect")) {
            connectVbox.setDisable(false);
            connectVbox.setVisible(true);
        } else if (connectButton.getText().equals("Requested")){
            try {
                HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + currentUserEmail + "/" + profileUserEmail,
                        HttpMethod.DELETE, null, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            connectButton.setText("Connect");
            initialize();
        }
    }

    @FXML
    void on_connectionCountLabel_clicked(MouseEvent event) {
        initializeConnectListView(true);

        HttpResponse connectsResponse;

        try {
            connectsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (connectsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        connects = gson.fromJson(connectsResponse.getBody(), CONNECT_LIST_TYPE);

        for (User user : allUsers) {
            for (Connect connect : connects) {
                if (connect.getSender().equals(profileUserEmail) && !connect.isPending()){
                    if (connect.getReceiver().equals(user.getEmail()))
                        users.add(user);
                }
                else if (connect.getReceiver().equals(profileUserEmail) && !connect.isPending()){
                    if (connect.getSender().equals(user.getEmail()))
                        users.add(user);
                }
            }
        }

        usersListView.setItems(users);
    }

    @FXML
    void on_closeConnectLW_clicked(ActionEvent event) {
        connectionListViewVbox.setVisible(false);
    }

    private void initializeConnectListView(boolean visibility) {
        users.clear();
        if (connects != null) connects.clear();

        connectionListViewVbox.setVisible(visibility);

        connectionListView.setItems(users);
        connectionListView.setCellFactory(
                new Callback<ListView<User>, ListCell<User>>() {
                    @Override
                    public ListCell<User> call(ListView<User> param) {
                        return new ProfileSearchCell();
                    }
                }
        );
        connectionListView.prefHeightProperty().bind(Bindings.size(users).multiply(80));
        connectionListView.setOnMouseClicked(this::handleConnectionListViewClick);

        HttpResponse allUsersResponse;
        try {
            allUsersResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (allUsersResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        allUsers = gson.fromJson(allUsersResponse.getBody(), USER_LIST_TYPE);
    }

    private int calculateConnections() {
        HttpResponse connectsResponse;

        try {
            connectsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/connect/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (connectsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        connects = gson.fromJson(connectsResponse.getBody(), CONNECT_LIST_TYPE);
        int i = 0;
        for (Connect connect : connects) {
            if (!connect.isPending()){
                i++;
            }
        }

        return i;
    }

    private void handleConnectionListViewClick(MouseEvent event) {
        User selectedUser = connectionListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ProfileController.setProfileUserEmail(selectedUser.getEmail());
            switchScenes("/fxml/profileView.fxml", connectionListViewVbox);
        }
    }

    ////////////////////////////////////////// ___ server response ___ /////////////////////////////////////////////////

    private HttpResponse getUserResponse(){
        HttpResponse userResponse;
        try {
            userResponse = HttpController.sendRequest(SERVER_ADDRESS + "/users/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (userResponse.getStatusCode() != 200) throw new RuntimeException("Error getting user data");

        return userResponse;
    }

    private HttpResponse getEducationResponse(){
        HttpResponse educationResponse;
        try {
            educationResponse = HttpController.sendRequest(SERVER_ADDRESS + "/education/" + profileUserEmail ,HttpMethod.GET , null , null);
        } catch (IOException e){
            throw  new RuntimeException(e);
        }

        if (educationResponse.getStatusCode() != 200) throw new RuntimeException("Error getting education data");
        return  educationResponse;
    }

    private HttpResponse getContactsInfoResponse(){
        HttpResponse contactsInfoResponse;
        try {
            contactsInfoResponse = HttpController.sendRequest(SERVER_ADDRESS + "/contacts/" + profileUserEmail , HttpMethod.GET , null , null);
        }catch (IOException e){
            throw  new RuntimeException(e);
        }

        if (contactsInfoResponse.getStatusCode() != 200) throw new RuntimeException("Error getting contactsInfo data");
        return  contactsInfoResponse;

    }

    private HttpResponse getEducationSkillsResponse() {
        HttpResponse educationSkillsResponse;
        try {
            educationSkillsResponse = HttpController.sendRequest(SERVER_ADDRESS + "/educationSkills/" + profileUserEmail, HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (educationSkillsResponse.getStatusCode() != 200) throw new RuntimeException("Error getting contactsInfo data");
        return  educationSkillsResponse;
    }

    //////////////////////////////////////////// ___ response Json ___ /////////////////////////////////////////////////

    private JsonNode getUserJson(HttpResponse userResponse){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(userResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode getEducationJson(HttpResponse EducationResponse){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (EducationResponse.getBody().equals("No such education info found!")) return null;
            return objectMapper.readTree(EducationResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode getEducatinSkillsJson(HttpResponse educationSkillResponse){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (educationSkillResponse.getBody().equals("No such education skills info found!")) return null;
            return objectMapper.readTree(educationSkillResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode getContactsJson(HttpResponse contactsResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (contactsResponse.getBody().equals("No such contact found!")) return null;
            return objectMapper.readTree(contactsResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////// ___ contacts bar ___ /////////////////////////////////////////////////

    @FXML
    void on_editContactButton_clicked(ActionEvent event) {
        contactsInfoVbox.setDisable(false);
        contactsInfoVbox.setVisible(true);
        contactLimitLabel.setVisible(false);
    }
    @FXML
    void on_cancelContactsButton_clicked(ActionEvent event) {
        contactsInfoVbox.setVisible(false);
        contactsInfoVbox.setDisable(true);
        contactLimitLabel.setVisible(false);
    }

    @FXML
    void on_saveContactsButton_clicked(ActionEvent event) {
        String newContactEmail = emailContactsTF.getText();
        String newNumber = numberContactsTF.getText();
        RadioButton selectedButton = (RadioButton) numberFormat.getSelectedToggle();
        String newPhoneType = selectedButton == null ? "" : selectedButton.getText();
        String newAddress = addressContactsTF.getText();
        String newOtherAccs = otherAccountsContactsTF.getText();
        LocalDate birthdayLD = birthdayContactsDP.getValue();
        java.sql.Date newBirthday = birthdayLD == null ? null : java.sql.Date.valueOf(birthdayLD);

        String birthdayVisibility = ((RadioButton)birthDayToggle.getSelectedToggle()).getText();

        ContactsInfo contactsInfo = null;
        try {
            contactsInfo = new ContactsInfo(1, profileUserEmail, newContactEmail, newNumber, newPhoneType, newAddress, newBirthday, newOtherAccs, birthdayVisibility);
        } catch (CharacterNumberLimitException e) {
            contactLimitLabel.setVisible(true);
            return;
        }

        String contactJson = gson.toJson(contactsInfo);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/contacts", HttpMethod.PUT, contactJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        contactsInfoVbox.setDisable(true);
        contactsInfoVbox.setVisible(false);
        initialize();
    }

    //////////////////////////////////////////// ___ education bar ___ /////////////////////////////////////////////////

    private ArrayList<Education> allEducation;
    @FXML
    void on_editEduButton_clicked(ActionEvent event) {
        educationVbox.setDisable(false);
        educationVbox.setVisible(true);
        educationLimitLabel.setVisible(false);

    }

    @FXML
    void on_cancelEduButton_Clicked(ActionEvent event) {
        educationVbox.setVisible(false);
        educationVbox.setDisable(true);
        educationLimitLabel.setVisible(false);
    }

    @FXML
    void on_saveEduButton_clicked(ActionEvent event) {
        String newInstituteName = instituteNameEduTF.getText();
        String newMajor = majorEduTF.getText();
        String newGrade = gradeEduTF.getText();
        String newActivityDescription = activityEduTF.getText();
        String newAdditionalInformation = additionalEduTF.getText();
        LocalDate localDate1 = registerDateDP.getValue();
        java.sql.Date newRegisterDate = localDate1 == null ? null : java.sql.Date.valueOf(localDate1);
        LocalDate localDate2 = graduateDateDP.getValue();
        java.sql.Date newGraduateDate = localDate2 == null ? null : java.sql.Date.valueOf(localDate2);
        TextField[] skills = new TextField[] {skill1SkillsTF, skill2SkillsTF, skill3SkillsTF, skill4SkillsTF, skill5SkillsTF};
        ArrayList<String> newSkills = new ArrayList<>();

        for (TextField skill : skills){
            newSkills.add(skill.getText());
        }

        Education education = null;
        try {
            education = new Education(1, profileUserEmail , newInstituteName , newMajor, newRegisterDate ,
                    newGraduateDate , newGrade , newActivityDescription ,newSkills, newAdditionalInformation);
        } catch (CharacterNumberLimitException e) {
            educationLimitLabel.setVisible(true);
            return;
        }


        String educationJson = gson.toJson(education);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/education", HttpMethod.POST, educationJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        educationVbox.setDisable(true);
        educationVbox.setVisible(false);
        initialize();
    }

    @FXML
    void on_showAllEduLabel_clicked(MouseEvent event) {
        showAllEduVbox.setVisible(true);
        showAllEduVbox.setDisable(false);

        HttpResponse educations;

        try {
            educations = HttpController.sendRequest(SERVER_ADDRESS + "/education", HttpMethod.GET, null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allEducation = gson.fromJson(educations.getBody() , EDUCATION_LIST_TYPE);

        //Reverse the collection
        Collections.reverse(allEducation);

        // Clear the grid pane before adding new edu
        showAllEduGridpane.getChildren().clear();

        int row = 1;
        for (Education edu : allEducation){
            if (edu.getEmail().contains(profileUserEmail)){
                row = loadConnect(edu , row);
            }
        }

    }

    private int loadConnect (Education edu , int row){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/allEducationView.fxml"));
            VBox educationView = loader.load(); // Load the FXML file and get the root element

            AllEducationController controller = loader.getController();
            controller.initializeEdu(edu);
            showAllEduGridpane.add(educationView , 0 , row);

//            showAllEduGridpane.add(educationView, 0, row);
        }catch(IOException e){
            throw  new RuntimeException();
        }
        return row + 1;


    }

    @FXML
    void on_showAllEduLabel_enterd(MouseEvent event) {
        showAllEduLabel.setStyle("-fx-underline: true");
        showAllEduLabel.setStyle("-fx-font-weight: 800");


    }

    @FXML
    void on_showAllEduLabel_exit(MouseEvent event) {
        showAllEduLabel.setStyle("-fx-underline: false");
        showAllEduLabel.setStyle("-fx-font-weight: 10");
    }

    @FXML
    void on_showAllEduCloseButton_clicked(ActionEvent event) {
        showAllEduVbox.setVisible(false);
        showAllEduVbox.setDisable(true);

    }

    @FXML
    void on_addEduButton_clicked(ActionEvent event) {
        addEduVbox.setDisable(false);
        addEduVbox.setVisible(true);
    }

    @FXML
    void on_cancelEduAddButton_clicked(ActionEvent event) {
        addEduVbox.setVisible(false);
        addEduVbox.setDisable(true);
    }

    @FXML
    void on_addEduVboxButton_clicked(ActionEvent event) {
        String newInstituteName = instituteEduAddTF.getText();
        String newMajor = majorEduAddTF.getText();
        String newGrade = gradeEduAddTF.getText();
        String newActivityDescription = activityEduAddTA.getText();
        String newAdditionalInformation = additionalEduAddTA.getText();
        LocalDate localDate1 = registerDateAddDP.getValue();
        java.sql.Date newRegisterDate = localDate1 == null ? null : java.sql.Date.valueOf(localDate1);
        LocalDate localDate2 = graduateDateAddDP.getValue();
        java.sql.Date newGraduateDate = localDate2 == null ? null : java.sql.Date.valueOf(localDate2);
        TextField[] skills = new TextField[] {skill1SkillsTF, skill2SkillsTF, skill3SkillsTF, skill4SkillsTF, skill5SkillsTF};
        ArrayList<String> newSkills = new ArrayList<>();

        for (TextField skill : skills){
            newSkills.add(skill.getText());
        }

        Random random = new Random();

        Education education = null;
        try {
            education = new Education(1, profileUserEmail , newInstituteName , newMajor, newRegisterDate ,
                    newGraduateDate , newGrade , newActivityDescription ,newSkills, newAdditionalInformation);
        } catch (CharacterNumberLimitException e) {
            educationLimitLabel.setVisible(true);
            return;
        }


        String educationJson = gson.toJson(education);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/education", HttpMethod.PUT, educationJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addEduVbox.setDisable(true);
        addEduVbox.setVisible(false);
    }

    ////////////////////////////////////////////// ___ skills bar ___ //////////////////////////////////////////////////

    @FXML
    void on_editSkillsButton_clicked(ActionEvent event) {
        skillsVbox.setDisable(false);
        skillsVbox.setVisible(true);
    }

    @FXML
    void on_cancelSkillsButton_clicked(ActionEvent event) {
        skillsVbox.setVisible(false);
        skillsVbox.setDisable(true);
    }

    @FXML
    void on_saveSkillsButton_clicked(ActionEvent event) {
        EducationSkills educationSkills = new EducationSkills(profileUserEmail, skill1SkillsTF.getText(),
                skill2SkillsTF.getText(), skill3SkillsTF.getText(), skill4SkillsTF.getText(), skill5SkillsTF.getText());

        String eduSkillsJson = gson.toJson(educationSkills);

        try {
            HttpResponse response = HttpController.sendRequest(SERVER_ADDRESS + "/educationSkills", HttpMethod.PUT, eduSkillsJson, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        skillsVbox.setDisable(true);
        skillsVbox.setVisible(false);
        initialize();
    }



    /////////////////////////////////////// ___ getters and setters ___ ////////////////////////////////////////////////

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static void setCurrentUserEmail(String currentUserEmail) {
        ProfileController.currentUserEmail = currentUserEmail;
    }

    public static String getProfileUserEmail() {
        return profileUserEmail;
    }

    public static void setProfileUserEmail(String profileUserEmail) {
        ProfileController.profileUserEmail = profileUserEmail;
    }

}