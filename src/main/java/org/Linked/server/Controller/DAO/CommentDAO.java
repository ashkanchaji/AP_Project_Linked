package org.Linked.server.Controller.DAO;

import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.Comment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDAO extends AbstractPostDAO<Comment> {
    private final String CREATE_COMMENT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "commentId VARCHAR(36) NOT NULL, "
            + "postId VARCHAR(36) NOT NULL, "
            + "posterID VARCHAR(45), "
            + "text VARCHAR(3000), "
            + "likes INT, "
            + "comments_count INT, "
            + "createdAT DATE, "
            + "repost_count INT, "
            + "repliedUser VARCHAR(1250)"
            + ")";

    private final String INSERT_COMMENT_SQL = "INSERT INTO " + tablePath +
            "(commentId, postId, posterID, text, likes, comments_count, createdAT, repost_count, repliedUser) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_COMMENT_SQL = "UPDATE " + tablePath + " SET " +
            "text = ?, likes = ?, comments_count = ?, createdAT = ?, repost_count = ?, repliedUser = ? " +
            "WHERE commentId = ?";

    public CommentDAO() {
        super("comments");
    }

    @Override
    protected Comment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        try {
            return new Comment(resultSet.getString("commentId"),
                    resultSet.getString("postId"),
                    resultSet.getString("posterID"),
                    resultSet.getString("text"),
                    resultSet.getInt("likes"),
                    resultSet.getInt("comments_count"),
                    resultSet.getDate("createdAT"),
                    resultSet.getInt("repost_count"),
                    resultSet.getString("repliedUser"));
        } catch (CharacterNumberLimitException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_COMMENT_TABLE_SQL;
    }

    public void saveComment(Comment comment) throws SQLException {
        saveEntity(comment, INSERT_COMMENT_SQL, (ps, c) -> {
            ps.setString(1, c.getCommentId());
            ps.setString(2, c.getPostId());
            ps.setString(3, c.getUserId());
            ps.setString(4, c.getText());
            ps.setInt(5, c.getLikes());
            ps.setInt(6, c.getComments());
            ps.setDate(7, c.getCreatedAt());
            ps.setInt(8, c.getReposts());
            ps.setString(9, c.getRepliedUser());
        });
    }

    public Comment getCommentById(String commentId) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE commentId = ?";
        return getEntity(query, commentId);
    }

    public ArrayList<Comment> getAllComments() throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateComment(Comment comment) throws SQLException {
        checkTableExistence();
        updatePost(comment, UPDATE_COMMENT_SQL);
    }

    public void deleteCommentById(String commentId) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE commentId = ?";
        deleteEntity(query, commentId);
    }

    public void deleteAllComments() throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
