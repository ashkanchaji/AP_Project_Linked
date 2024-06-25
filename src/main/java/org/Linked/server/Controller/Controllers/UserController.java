package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.Parsers.JwtUtil;
import org.Linked.server.Controller.Util.UserTypeAdapter;
import org.Linked.server.Model.ContactsInfo;
import org.Linked.server.Model.Education;
import org.Linked.server.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController extends Controller{
    public static String getUsers() throws SQLException {
        ArrayList<User> users = UserDAO.getAllUsers();
        return gson.toJson(users);
    }

    public static String getUser(String email) throws SQLException {
        User user = UserDAO.getUserByEmail(email);
        return user == null ? null : gson.toJson(user);
    }

    public static void createUser (String json) throws SQLException{
        User user = gson.fromJson(json, User.class);

        if (UserDAO.getUserByEmail(user.getEmail()) != null){
            UserDAO.updateUser(user);
        } else {
            UserDAO.saveUser(user);
            String userToken = JwtUtil.generateToken(json);
            UserDAO.addUserJWT(userToken, user);
        }
    }

    public static void updateUser(String json) throws SQLException {
        User user = gson.fromJson(json, User.class);

        UserDAO.updateUser(user);
    }

    public static void deleteUser(String json) throws SQLException {
        User user = gson.fromJson(json, User.class);

        UserDAO.deleteUserByEmail(user.getEmail());
    }

    public static void deleteUsers() throws SQLException {
        UserDAO.deleteAllUsers();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getEducation (String email) throws SQLException {
        Education education = EducationDAO.getEducationByEmail(email);
        return education == null ? null : gson.toJson(education);
    }

    public static String getAllEducations () throws SQLException {
        ArrayList<Education> educations = EducationDAO.getAllEducations();
        return gson.toJson(educations);
    }

    public static void createEducation (String json) throws SQLException {
        Education education = gson.fromJson(json, Education.class);

        if (UserDAO.getUserByEmail(education.getEmail()) == null) throw new SQLException("User does not exist"); // ***

        if (EducationDAO.getEducationByEmail(education.getEmail()) == null){
            EducationDAO.saveEducation(education);
        } else {
            EducationDAO.updateEducation(education);
        }
    }

    public static void deleteEducation (String email) throws SQLException {
        EducationDAO.deleteEducationByEmail(email);
    }

    public static void deleteAllEducations () throws SQLException {
        EducationDAO.deleteAllEducations();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getContactInfo (String email) throws SQLException{
        ContactsInfo contactsInfo = ContactsInfoDAO.getContactsInfoByEmail(email);
        return contactsInfo == null ? null : gson.toJson(contactsInfo);
    }

    public static String getAllContactInfos () throws SQLException {
        ArrayList<ContactsInfo> contacts = ContactsInfoDAO.getAllContactsInfo();
        return gson.toJson(contacts);
    }

    public static void createContact (String json) throws SQLException{
        ContactsInfo contactsInfo = gson.fromJson(json, ContactsInfo.class);

        if (UserDAO.getUserByEmail(contactsInfo.getEmail()) == null) throw new SQLException("User does not exist"); // ***

        if (ContactsInfoDAO.getContactsInfoByEmail(contactsInfo.getEmail()) == null){
            ContactsInfoDAO.saveContactsInfo(contactsInfo);
        } else {
            ContactsInfoDAO.updateContactsInfo(contactsInfo);
        }
    }

    public static void updateContacts (String json) throws SQLException {
        ContactsInfo contactsInfo = gson.fromJson(json, ContactsInfo.class);

        ContactsInfoDAO.updateContactsInfo(contactsInfo);
    }

    public static void deleteContact (String email) throws SQLException{
        ContactsInfoDAO.deleteContactsInfoByEmail(email);
    }

    public static void deleteAllContacts () throws SQLException {
        ContactsInfoDAO.deleteAllContactsInfo();
    }
}
