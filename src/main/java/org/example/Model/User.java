package org.example.Model;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Controller.Parsers.EmailValidator;
import org.example.Controller.Parsers.PasswordValidator;

import java.sql.*;

public class User extends Model{
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private static final Connection connection = MySqlDB.fetchDB().getConnection();
    private static final String tableName = "users_info";
    private static final String createUsersTableSQL = "CREATE TABLE projectlinked.users_info ("
            + "email VARCHAR(45) PRIMARY KEY, "
            + "password VARCHAR(45), "
            + "first_name VARCHAR(20), "
            + "lastname VARCHAR(40), "
            + "jwt_hash VARCHAR(1000)"
            + ")";

    public User(String email, String firstName, String lastName, String password) throws InvalidEmailException, InvalidPassException {
        if (EmailValidator.isValidEmail(email) && EmailValidator.isUniqueEmail(email)){
            this.email = email;
        } else {
            throw new InvalidEmailException();
        }

        if (PasswordValidator.containsNumsAndChars(password) && PasswordValidator.checkPasswordLength(password)){
            this.password = password;
        } else {
            throw new InvalidPassException();
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addUserToDB(){
        try (Statement statement = connection.createStatement()) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
               statement.execute(createUsersTableSQL);
            }

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    MySqlDB.getDBName() + "." + tableName +
                    " (email, password, first_name, last_name) VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, this.email);
            preparedStatement.setString(2, this.password);
            preparedStatement.setString(3, this.firstName);
            preparedStatement.setString(4, this.lastName);

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public void addUserJWT(String jwt) {
        String sql = "UPDATE " + MySqlDB.getDBName() + "." + tableName +
                " SET jwt_hash = ? WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jwt);
            preparedStatement.setString(2, this.email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean doesUserExist(String email){
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.getResultSet();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
