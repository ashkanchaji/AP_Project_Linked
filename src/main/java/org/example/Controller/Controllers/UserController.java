package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.DAO.ContactsInfoDAO;
import org.example.Controller.DAO.EducationDAO;
import org.example.Controller.DAO.UserDAO;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.JwtUtil;
import org.example.Controller.Parsers.OutPut;
import org.example.Model.ContactsInfo;
import org.example.Model.Education;
import org.example.Model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController extends Controller{
    private static final Gson gson = new Gson();

    public static String getUsers() throws SQLException {
        ArrayList<User> users = UserDAO.getUsers();
        return gson.toJson(users);
    }

    public static String getUser(String email) throws SQLException {
        User user = UserDAO.getUser(email);
        return user == null ? null : gson.toJson(user);
    }

    public static void createUser (String json) throws SQLException{
        User user = gson.fromJson(json, User.class);

        if (UserDAO.getUser(user.getEmail()) != null){
            UserDAO.updateUser(user);
        } else {
            UserDAO.saveUser(user);
        }
    }

    public static void updateUser(String json) throws SQLException {
        User user = gson.fromJson(json, User.class);

        UserDAO.updateUser(user);
    }

    public static void deleteUser(String json) throws SQLException {
        User user = gson.fromJson(json, User.class);

        UserDAO.deleteUser(user);
    }

    public static void deleteUsers() throws SQLException {
        UserDAO.deleteUsers();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getEducation (String email) throws SQLException {
        Education education = EducationDAO.getEducation(email);
        return education == null ? null : gson.toJson(education);
    }

    public static String getAllEducations () throws SQLException {
        ArrayList<Education> educations = EducationDAO.getAllEducation();
        return gson.toJson(educations);
    }

    public static void createEducation (String json) throws SQLException {
        Education education = gson.fromJson(json, Education.class);

        if (UserDAO.getUser(education.getEmail()) == null) throw new SQLException("User does not exist"); // ***

        if (EducationDAO.getEducation(education.getEmail()) == null){
            EducationDAO.saveEducation(education);
        } else {
            EducationDAO.updateEducation(education);
        }
    }

    public static void deleteEducation (String email) throws SQLException {
        EducationDAO.deleteEducation(email);
    }

    public static void deleteAllEducations () throws SQLException {
        EducationDAO.deleteAllEducations();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String getContactInfo (String email) throws SQLException{
        ContactsInfo contactsInfo = ContactsInfoDAO.getContactsInfo(email);
        return contactsInfo == null ? null : gson.toJson(contactsInfo);
    }

    public static String getAllContactInfos () throws SQLException {
        ArrayList<ContactsInfo> contacts = ContactsInfoDAO.getAllContactsInformations();
        return gson.toJson(contacts);
    }

    public static void createContact (String json) throws SQLException{
        ContactsInfo contactsInfo = gson.fromJson(json, ContactsInfo.class);

        if (UserDAO.getUser(contactsInfo.getEmail()) == null) throw new SQLException("User does not exist"); // ***

        if (ContactsInfoDAO.getContactsInfo(contactsInfo.getEmail()) == null){
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
        ContactsInfoDAO.deleteContactsInfo(email);
    }

    public static void deleteAllContacts () throws SQLException {
        ContactsInfoDAO.deleteAllContactsInformation();
    }
}
