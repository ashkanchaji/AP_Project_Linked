package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.Comment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO extends AbstractPostDAO<Comment> {
    private final String CREATE_COMMENT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "postId VARCHAR(36) NOT NULL, "
            + "posterID VARCHAR(45), "
            + "text VARCHAR(3000), "
            + "likes INT, "
            + "comments_count INT, "
            + "createdAT DATE, "
            + "repost_count INT, "
            + "byte_file_path VARCHAR(1000), "
            + "repliedUser VARCHAR(1250)"
            + ")";

    private final String INSERT_COMMENT_SQL = "INSERT INTO " + tablePath +
            "(postId, posterID, text, likes, comments_count, createdAT, repost_count, byte_file_path, repliedUser) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_COMMENT_SQL = "UPDATE " + tablePath + " SET " +
            "text = ?, likes = ?, comments_count = ?, createdAT = ?, repost_count = ?, byte_file_path = ?, repliedUser = ? " +
            "WHERE postId = ?";

    public CommentDAO() {
        super("comments");
    }

    @Override
    protected Comment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Comment(resultSet.getString("postId"),
                resultSet.getString("posterID"),
                resultSet.getString("text"),
                resultSet.getInt("likes"),
                resultSet.getInt("comments_count"),
                resultSet.getDate("createdAT"),
                resultSet.getInt("repost_count"),
                resultSet.getString("byte_file_path"),
                resultSet.getString("repliedUser"));
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_COMMENT_TABLE_SQL;
    }

    public void saveComment(Comment comment) throws SQLException {
        saveEntity(comment, INSERT_COMMENT_SQL, (ps, c) -> {
            ps.setString(1, c.getPostId());
            ps.setString(2, c.getUserId());
            ps.setString(3, c.getText());
            ps.setInt(4, c.getLikes());
            ps.setInt(5, c.getComments());
            ps.setDate(6, c.getCreatedAt());
            ps.setInt(7, c.getReposts());
            ps.setString(8, c.getByteFilePath());
            ps.setString(9, c.getRepliedUser());
        });
    }

    public Comment getCommentByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE posterID = ?";
        return getEntity(query, email);
    }

    public Comment getCommentById(String commentId) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE postId = ?";
        return getEntity(query, commentId);
    }

    public ArrayList<Comment> getAllComments() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateComment(Comment comment) throws SQLException {
        updatePost(comment, UPDATE_COMMENT_SQL);
    }

    public void deleteCommentByEmail(String email, Date date) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE posterID = ? AND createdAT = ?";
        deletePostByEmail(email, date, query); // Date parameter is null for Comments
    }

    public void deleteCommentById(String commentId) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE postId = ?";
        deletePostByEmail(commentId, null, query); // Date parameter is null for Comments
    }

    public void deleteAllComments() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
