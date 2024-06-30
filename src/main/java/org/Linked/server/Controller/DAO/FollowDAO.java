package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Follow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowDAO extends GenericDAO<Follow> {
    private final String CREATE_FOLLOWS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "follower VARCHAR(45), "
            + "following VARCHAR(45)"
            + ")";

    public FollowDAO() {
        super("follows_info");  // Corrected table name
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
        return CREATE_FOLLOWS_TABLE_SQL;
    }

    public void saveFollow(Follow follow) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(follower, following) " +
                "VALUES (?, ?)";
        saveEntity(follow, query, (ps, j) -> {
            ps.setString(1, j.getFollower());
            ps.setString(2, j.getFollowing());
        });
    }

    public Follow getFollowByEmail(String followerEmail, String followingEmail) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE follower = ? AND following = ?";
        checkTableExistence();

        Follow entity = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, followerEmail);
            statement.setString(2, followingEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        }
        return entity;
    }

    public ArrayList<Follow> getAllFollows() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateFollow(Follow follow) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET following = ? " +
                "WHERE follower = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, follow.getFollowing());  // Corrected parameter order
            ps.setString(2, follow.getFollower());
            ps.executeUpdate();
        }
    }

    public void deleteFollowByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE follower = ?";
        deleteEntity(query, email);
    }

    public void deleteAllFollows() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }

    public ArrayList<Follow> getFollowsByFollower(String follower) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE follower = ?";
        return getEntities(query, follower);
    }

    // This method can use the existing getAllEntities method as a template
    private ArrayList<Follow> getEntities(String query, String parameter) throws SQLException {
        checkTableExistence();

        ArrayList<Follow> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        }
        return entities;
    }
}
