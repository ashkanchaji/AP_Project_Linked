package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Education;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationDAO extends GenericDAO<Education> {
    private final String CREATE_EDUCATION_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "college_name VARCHAR(40), "
            + "major VARCHAR(40), "
            + "enter_year DATE, "
            + "exit_year DATE, "
            + "grade VARCHAR(40), "
            + "activities VARCHAR(500), "
            + "additional_info VARCHAR(1000), "
            + "skills VARCHAR(1000)"
            + ")";

    public EducationDAO() {
        super("educations");
    }

    @Override
    protected Education mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Education(
                resultSet.getString("email"),
                resultSet.getString("college_name"),
                resultSet.getString("major"),
                resultSet.getDate("enter_year"),
                resultSet.getDate("exit_year"),
                resultSet.getString("grade"),
                resultSet.getString("activities"),
                gson.fromJson(resultSet.getString("skills"), arrayListType),
                resultSet.getString("additional_info")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_EDUCATION_TABLE_SQL;
    }

    public void saveEducation(Education education) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, college_name, major, enter_year, exit_year, grade, activities, additional_info, skills) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        saveEntity(education, query, (ps, e) -> {
            ps.setString(1, e.getEmail());
            ps.setString(2, e.getCollegeName());
            ps.setString(3, e.getMajor());
            ps.setDate(4, e.getEnterYear());
            ps.setDate(5, e.getExitYear());
            ps.setString(6, e.getGrade());
            ps.setString(7, e.getActivitiesInfo());
            ps.setString(8, e.getAdditionalInfo());
            ps.setString(9, gson.toJson(e.getSkills()));
        });
    }

    public Education getEducationByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<Education> getAllEducations() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateEducation(Education education) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET college_name = ?, major = ?, enter_year = ?, exit_year = ?, grade = ?, " +
                "activities = ?, additional_info = ?, skills = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, education.getCollegeName());
            ps.setString(2, education.getMajor());
            ps.setDate(3, education.getEnterYear());
            ps.setDate(4, education.getExitYear());
            ps.setString(5, education.getGrade());
            ps.setString(6, education.getActivitiesInfo());
            ps.setString(7, education.getAdditionalInfo());
            ps.setString(8, gson.toJson(education.getSkills()));
            ps.setString(9, education.getEmail());
            ps.executeUpdate();
        }
    }

    public void deleteEducationByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllEducations() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
