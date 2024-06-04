package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobDAO extends GenericDAO<Job> {
    private final String CREATE_JOBS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "name VARCHAR(40), "
            + "location VARCHAR(40), "
            + "additional_info VARCHAR(1000), "
            + "skills VARCHAR(1000)"
            + ")";

    public JobDAO() {
        super("jobs_info");
    }

    @Override
    protected Job mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Job(
                resultSet.getString("email"),
                resultSet.getString("name"),
                resultSet.getString("location"),
                resultSet.getString("additional_info"),
                gson.fromJson(resultSet.getString("skills"), arrayListType)
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_JOBS_TABLE_SQL;
    }

    public void saveJob(Job job) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, name, location, additional_info, skills) " +
                "VALUES (?, ?, ?, ?, ?)";
        saveEntity(job, query, (ps, j) -> {
            ps.setString(1, j.getEmail());
            ps.setString(2, j.getName());
            ps.setString(3, j.getLocation());
            ps.setString(4, j.getAdditionalInfo());
            ps.setString(5, gson.toJson(j.getSkills()));
        });
    }

    public Job getJobByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";
        return getEntity(query, email);
    }

    public ArrayList<Job> getAllJobs() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateJob(Job job) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET name = ?, location = ?, additional_info = ?, skills = ? " +
                "WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, job.getName());
            ps.setString(2, job.getLocation());
            ps.setString(3, job.getAdditionalInfo());
            ps.setString(4, gson.toJson(job.getSkills()));
            ps.setString(5, job.getEmail());
            ps.executeUpdate();
        }
    }

    public void deleteJobByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllJobs() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
