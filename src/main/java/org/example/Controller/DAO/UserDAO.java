package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;
import org.example.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends DAO{
    private static final String tableName = "users_info";
    private static final String createUsersTableSQL = "CREATE TABLE "
            + MySqlDB.getDBName() + "." + tableName + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "password VARCHAR(45), "
            + "first_name VARCHAR(20), "
            + "last_name VARCHAR(40), "
            + "jwt_hash VARCHAR(1000), "
            + "additional_name VARCHAR(20), "
            + "profile_picture_path VARCHAR(1000), "
            + "background_picture_path VARCHAR(1000), "
            + "headline VARCHAR(200), "
            + "country VARCHAR(20), "
            + "city VARCHAR(20), "
            + "profession VARCHAR(60)"
            + ")";


    public static void addUserToDB(User user){
        try (Statement statement = connection.createStatement()) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
                statement.execute(createUsersTableSQL);
            }

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    MySqlDB.getDBName() + "." + tableName +
                    " (email, password, first_name, last_name) VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e){
            e.printStackTrace();

        }
    }

    public static void addUserJWT(String jwt, User user) {
        String sql = "UPDATE " + MySqlDB.getDBName() + "." + tableName +
                " SET jwt_hash = ? WHERE email = ?";

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
            ResultSet resultSet = statement.getResultSet();

            if (resultSet == null){
                return false;
            }

            while (resultSet.next()){
                if (resultSet.getString("email").equals(email)){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
