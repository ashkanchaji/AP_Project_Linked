package org.Linked.server.Controller.DAO;
import org.Linked.server.Model.Like;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikeDAO extends  GenericDAO<Like> {

    private final String CREATE_LIKES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "liker VARCHAR(45), "
            + "liked VARCHAR(45)"
            + ")";

    public LikeDAO() {
        super("likes_info");
    }

    @Override
    protected Like mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Like(
                resultSet.getString("liker"),
                resultSet.getString("liked")
        );
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_LIKES_TABLE_SQL;
    }
    public void saveLike(Like like) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                " (liker , liked) " +
                "VALUES (?, ?)";
        saveEntity(like, query, (ps, j) -> {
            ps.setString(1, j.getLiker());
            ps.setString(2, j.getLiked());
        });
    }

    public Like getLikeByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE liker = ?";
        return getEntity(query, email);
    }

    public ArrayList<Like> getAllLikes() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }
    public void updateLike(Like like) throws SQLException {
        String query = "UPDATE " + tablePath +
                " SET liked = ? " +
                "WHERE liker = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, like.getLiked());
            ps.setString(2, like.getLiker());
            ps.executeUpdate();
        }
    }
    public void deleteLikeByEmail(String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE liker = ?";
        deleteEntity(query, email);
    }

    public void deleteAllLikes() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }


}
