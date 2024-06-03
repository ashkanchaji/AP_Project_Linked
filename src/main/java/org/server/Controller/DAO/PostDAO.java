package org.server.Controller.DAO;

import org.server.Model.Post;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostDAO extends AbstractPostDAO<Post> {
    private final String CREATE_POST_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
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

    private final String INSERT_POST_SQL = "INSERT INTO " + tablePath +
            "(posterID, text, likes, comments_count, createdAT, repost_count, byte_file_path) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_POST_SQL = "UPDATE " + tablePath + " SET " +
            "text = ?, likes = ?, comments_count = ?, createdAT = ?, repost_count = ?, byte_file_path = ? " +
            "WHERE posterID = ?";

    public PostDAO() {
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

    public void savePost(Post post) throws SQLException {
        savePost(post, INSERT_POST_SQL);
    }

    public Post getPostByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE posterID = ?";
        return getPostByEmail(email, query);
    }

    public ArrayList<Post> getAllPosts() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllPosts(query);
    }

    public void updatePost(Post post) throws SQLException {
        updatePost(post, UPDATE_POST_SQL);
    }

    public void deletePostByEmail(String email, Date date) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE posterID = ? AND createdAT = ?";
        deletePostByEmail(email, date, query);
    }

    public void deleteAllPosts() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllPosts(query);
    }
}
