package org.example.Controller.DAO;

import org.example.Controller.DB.MySqlDB;
import org.example.Controller.Exeptions.CharacterNumberLimitException;
import org.example.Controller.Exeptions.InvalidEmailException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Model.Education;
import org.example.Model.Job;
import org.example.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobDAO extends DAO{
    private static final String tableName = "jobs_info";
    private static final String tablePath = MySqlDB.getDBName() + "." + tableName;
    private static final String createUsersTableSQL = "CREATE TABLE "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "email VARCHAR(45), "
            + "name VARCHAR(40), "
            + "location VARCHAR(40), "                        //enterYear & exitYear typesh Date e
            + "earning INT, "
            + "additional_info VARCHAR(1000), "
            + "skills VARCHAR(1000)"
            + ")";
    public static Job getJob (String email) throws SQLException{
        Job job = null;
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                job = returnJob(resultSet);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return job;
    }
    public static ArrayList<Job> getAllJobs () throws SQLException {
        ArrayList<Job> jobs = new ArrayList<>();
        String query = "SELECT * FROM " + tablePath;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Job job = returnJob(resultSet);
                jobs.add(job);
            }
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }

        return jobs;
    }
    public static void saveJob (Job job) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email ,name, location, earning, additional_info, skills) " +
                "VALUES (? ,?, ?, ? ,? ,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            if (!MySqlDB.doesTableExist(connection, tableName)){
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(createUsersTableSQL);
                }
            }
            executePreparedStatement(ps, job);
        }
    }
    public static void updateJob(Job job) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET  email = ?, name = ?, location = ?, earning = ? , additional_info = ? , skills = ?,WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(10, job.getEmail());
            executePreparedStatement(ps, job);
        }
    }
    public static void deleteJob(Job job) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, job.getEmail());
            statement.executeUpdate();
        }
    }
    public static void deleteAllJob() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Job returnJob(ResultSet resultSet) throws SQLException, CharacterNumberLimitException {
        return new Job(resultSet.getString("email"),
                resultSet.getString("name"),
                resultSet.getString("location"),
                resultSet.getString("additionalInfo"),
                gson.fromJson(resultSet.getString("skills"), arrayListType));
    }

    private static void executePreparedStatement(PreparedStatement ps, Job job) throws SQLException {
        ps.setString(1, job.getEmail());
        ps.setString(2, job.getName());
        ps.setString(3, job.getLocation());
        ps.setString(4, job.getAdditionalInfo());
        ps.setString(5, gson.toJson(job.getSkills()));

        ps.executeUpdate();
    }



}
