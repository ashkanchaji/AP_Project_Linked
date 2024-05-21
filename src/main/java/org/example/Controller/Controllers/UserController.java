package org.example.Controller.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.DAO.UserDAO;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.JwtUtil;
import org.example.Controller.Parsers.OutPut;
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

        if (UserDAO.doesUserExist(user.getEmail())){
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
}
