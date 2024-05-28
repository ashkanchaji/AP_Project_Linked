package org.example.Controller.Parsers;

import org.example.Controller.DAO.UserDAO;
import org.example.Model.User;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final UserDAO USER_DAO = new UserDAO();

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isUniqueEmail(String email){
        User user;
        try {
            user = USER_DAO.getUserByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user == null;
    }
}

