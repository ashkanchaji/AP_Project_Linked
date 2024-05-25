package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAO{
    private static final String tableName = "users_info";
    private static final String tablePath = MySqlDB.getDBName() + "." + tableName;
    private static final String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "password VARCHAR(45), "
            + "first_name VARCHAR(20), "
            + "last_name VARCHAR(40), "
            + "jwt_hash VARCHAR(1000), "
            + "additional_name VARCHAR(40), "
            + "profile_picture_path VARCHAR(1000), "
            + "background_picture_path VARCHAR(1000), "
            + "headline VARCHAR(220), "
            + "country VARCHAR(60), "
            + "city VARCHAR(60), "
            + "profession VARCHAR(60)"
            + ")";

    public static void addUserJWT(String jwt, User user) {
        String sql = "UPDATE " + tablePath + " SET jwt_hash = ? WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jwt);
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // should be checked for efficiency
    public static boolean doesUserExist(String email){
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT email FROM " + tablePath);

            while (resultSet.next()){
                if (resultSet.getString("email").equals(email)){
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<User> getUsers() throws SQLException{
        ArrayList<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tablePath);

            while (resultSet.next()) {
                User user = returnUser(resultSet);

                users.add(user);
            }
        } catch (InvalidPassException | InvalidEmailException e) {
            throw new RuntimeException();
        }

        return users;
    }

    public static User getUser(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                user = returnUser(resultSet);
            }
        } catch (InvalidPassException | InvalidEmailException e) {
            throw new RuntimeException();
        }
        return user;
    }

    public static void saveUser(User user) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, password, first_name, last_name, additional_name, " +
                "profile_picture_path, background_picture_path, headline, country, city, profession) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(createUsersTableSQL);
                }
            }
            executePreparedStatement(ps, user);
        }
    }

    public static void updateUser(User user) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET email = ?, password = ?, first_name = ?, last_name = ?, additional_name = ?, " +
                "profile_picture_path = ?, background_picture_path = ?, headline = ?, country = ?, city = ?, " +
                "profession = ? WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(12, user.getEmail());
            executePreparedStatement(ps, user);
        }
    }

    // should check for password validation
    public static void deleteUser (User user) throws SQLException{
        String query = "DELETE FROM " + tablePath +" WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.executeUpdate();
        }
    }

    public static void deleteUsers () throws SQLException {
        String query = "DELETE FROM " + tablePath;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static User returnUser(ResultSet resultSet) throws SQLException, InvalidPassException, InvalidEmailException {
        User user = new User(resultSet.getString("email"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("password"),
                resultSet.getString("additional_name"),
                resultSet.getString("profile_picture_path"),
                resultSet.getString("background_picture_path"),
                resultSet.getString("headline"),
                resultSet.getString("country"),
                resultSet.getString("city"),
                resultSet.getString("profession"));

        return user;
    }

    private static void executePreparedStatement (PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setString(5, user.getAdditionalName());
        ps.setString(6, user.getProfilePicture());
        ps.setString(7, user.getBackgroundPicture());
        ps.setString(8, user.getHeadline());
        ps.setString(9, user.getCountry());
        ps.setString(10, user.getCity());
        ps.setString(11, user.getProfession());

        ps.executeUpdate();
    }
}
