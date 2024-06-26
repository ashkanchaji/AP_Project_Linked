package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends GenericDAO<User> {
    private final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
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

    public UserDAO() {
        super("users_info");
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("email"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("password"),
                resultSet.getString("additional_name"),
                resultSet.getString("profile_picture_path"),
                resultSet.getString("background_picture_path"),
                resultSet.getString("headline"),
                resultSet.getString("country"),
                resultSet.getString("city"),
                resultSet.getString("profession"),
                resultSet.getString("jwt_hash"));
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_USERS_TABLE_SQL;
    }

    public void saveUser(User user) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, password, first_name, last_name, additional_name, " +
                "profile_picture_path, background_picture_path, headline, country, city, profession) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        saveEntity(user, query, (ps, u) -> {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFirstName());
            ps.setString(4, u.getLastName());
            ps.setString(5, u.getAdditionalName());
            ps.setString(6, u.getProfilePicture());
            ps.setString(7, u.getBackgroundPicture());
            ps.setString(8, u.getHeadline());
            ps.setString(9, u.getCountry());
            ps.setString(10, u.getCity());
            ps.setString(11, u.getProfession());
        });
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE " + tablePath + " SET " +
                "password = ?, first_name = ?, last_name = ?, additional_name = ?, " +
                "profile_picture_path = ?, background_picture_path = ?, headline = ?, " +
                "country = ?, city = ?, profession = ? " +
                "WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getAdditionalName());
            ps.setString(5, user.getProfilePicture());
            ps.setString(6, user.getBackgroundPicture());
            ps.setString(7, user.getHeadline());
            ps.setString(8, user.getCountry());
            ps.setString(9, user.getCity());
            ps.setString(10, user.getProfession());
            ps.setString(11, user.getEmail());
            ps.executeUpdate();
        }
    }

    public void deleteUserByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllUsers() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addUserJWT(String jwt, String email) {
        String sql = "UPDATE " + tablePath + " SET jwt_hash = ? WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jwt);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // should be checked for efficiency
    @Deprecated
    public boolean doesUserExist(String email){
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
}
