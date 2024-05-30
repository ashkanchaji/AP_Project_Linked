package org.example.Controller.DAO;

import org.example.Model.Follow;
import org.example.Model.Job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowDAO extends GenericDAO<Follow> {
        private final String CREATE_FOLLOWS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + tablePath + " ("
                + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                + "follower VARCHAR(45), "
                + "following VARCHAR(45), "
                + ")";

        public FollowDAO() {
            super("jobs_info");
        }

        @Override
        protected Follow mapResultSetToEntity(ResultSet resultSet) throws SQLException {
            return new Follow(
                    resultSet.getString("follower"),
                    resultSet.getString("following")
            );
        }

        @Override
        protected String getCreateTableSQL() {
            return CREATE_FOLLOWS_TABLE;
        }
    public void saveFollow(Follow follow) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(email, name, location, additional_info, skills) " +
                "VALUES (?, ?, ?, ?, ?)";
        saveEntity(follow, query, (ps, j) -> {
            ps.setString(1, j.getFollower());
            ps.setString(2, j.getFollowing());
        });
    }

    public Follow getFollowByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE email = ?";      //email = follower?
        return getEntity(query, email);
    }

    public ArrayList<Follow> getAllFollows() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateFollow(Follow follow) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET following = ?" +
                "WHERE follower = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, follow.getFollower());
            ps.setString(2, follow.getFollowing());
            ps.executeUpdate();
        }
    }

    public void deleteFollowByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE email = ?";
        deleteEntity(query, email);
    }

    public void deleteAllFollows() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }






}
