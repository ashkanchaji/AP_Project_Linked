package org.example.Controller.DAO;

import org.example.Model.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostDAO extends GenericDAO<Post> {
    protected final String CREATE_POST_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "posterID VARCHAR(45), "
            + "text VARCHAR(3000), "
            + "likes INT, "
            + "comments_count INT, "
            + "createdAT DATE, "
            + "repost_count INT, "
            + "byte_file_path VARCHAR(1000)"
            + ")";

    public PostDAO(){
        super("posts");
    }

    @Override
    protected Post mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getString("posterID"),
                resultSet.getString("text"),
                resultSet.getInt("likes"),
                resultSet.getInt("comments_count"),
                resultSet.getDate("createdAT"),
                resultSet.getInt("repost_count"),
                resultSet.getString("byte_file_path"));
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_POST_TABLE_SQL;
    }

    public void savePost (Post post) throws SQLException {
        String query = "INSERT INTO " + tablePath +
                "(posterID, text, likes, comments_count, createdAT, repost_count, byte_file_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        saveEntity(post, query, (ps, p) -> {
            ps.setString(1, p.getUserId());
            ps.setString(2, p.getText());
            ps.setInt(3, p.getLikes());
            ps.setInt(4, p.getComments());
            ps.setDate(5, p.getCreatedAt());
            ps.setInt(6, p.getReposts());
            ps.setString(7, p.getByteFilePath());
        });
    }

    public Post getPostByEmail (String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE posterID = ?";
        return getEntity(query, email);
    }

    public ArrayList<Post> getAllPosts() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updatePost (Post post) throws SQLException {
        String query = "UPDATE " + tablePath + " SET " +
                "text = ?, likes = ?, comments_count = ?, createdAT = ?, repost_count = ?, byte_file_path = ? " +
                "WHERE posterID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, post.getText());
            ps.setInt(2, post.getLikes());
            ps.setInt(3, post.getComments());
            ps.setDate(4, post.getCreatedAt());
            ps.setInt(5, post.getReposts());
            ps.setString(6, post.getByteFilePath());
            ps.setString(7, post.getUserId());
        }
    }

    public void deletePostByEmail (String email) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE posterID = ?";
        deleteEntity(query, email);
    }

    public void deleteAllPosts() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
