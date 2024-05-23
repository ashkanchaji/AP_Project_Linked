package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.CharacterNumberLimitException;
import org.example.Model.Education;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EducationDAO extends DAO{
    private static final String tableName = "educations";
    private static final String tablePath = MySqlDB.getDBName() + "." + tableName;
    private static final String createEducationTableSQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45) , "
            + "college_name VARCHAR(40), "
            + "major VARCHAR(40), "
            + "enter_year DATE, "
            + "exit_year DATE, "
            + "grade VARCHAR(40), "
            + "activities VARCHAR(500), "
            + "additional_info VARCHAR(1000), "
            + "skills VARCHAR(1000)"
            + ")";

    public static Education getEducation (String email) throws SQLException{
        Education education = null;
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                education = returnEducation(resultSet);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return education;
    }

    public static ArrayList<Education> getAllEducation () throws SQLException {
        ArrayList<Education> educations = new ArrayList<>();
        String query = "SELECT * FROM " + tablePath;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Education education = returnEducation(resultSet);
                educations.add(education);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return educations;
    }

    public static void saveEducation (Education education) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, college_name, enter_year, exit_year, major, grade, activities, additional_info, skills) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(createEducationTableSQL);
                }
            }
            executePreparedStatement(ps, education);
        }
    }

    public static void updateEducation(Education education) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET email = ?, college_name = ?, major = ?, enter_year = ?, exit_year = ?, grade = ?, " +
                "activities = ?, skills = ?, additional_info = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(10, education.getEmail());
            executePreparedStatement(ps, education);
        }
    }

    public static void deleteEducation(Education education) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, education.getEmail());
            statement.executeUpdate();
        }
    }

    public static void deleteAllEducations() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Education returnEducation(ResultSet resultSet) throws SQLException, CharacterNumberLimitException {
        return new Education(resultSet.getString("email"),
                resultSet.getString("college_name"),
                resultSet.getString("major"),
                resultSet.getDate("enter_year"),
                resultSet.getDate("exit_year"),
                resultSet.getString("grade"),
                resultSet.getString("activities"),
                gson.fromJson(resultSet.getString("skills"), arrayListType),
                resultSet.getString("additional_info"));
    }

    private static void executePreparedStatement(PreparedStatement ps, Education education) throws SQLException {
        ps.setString(1, education.getEmail());
        ps.setString(2, education.getCollegeName());
        ps.setString(3, education.getMajor());
        ps.setDate(4, education.getEnterYear());
        ps.setDate(5, education.getExitYear());
        ps.setString(6, education.getGrade());
        ps.setString(7, education.getActivitiesInfo());
        ps.setString(8, gson.toJson(education.getSkills()));
        ps.setString(9, education.getAdditionalInfo());

        ps.executeUpdate();
    }

}
