package org.server.Controller.DAO;

import org.server.Model.Repost;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepostDAO extends AbstractPostDAO<Repost> {
    private final String CREATE_REPOST_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "posterID VARCHAR(45), "
            + "text VARCHAR(3000), "
            + "likes INT, "
            + "comments_count INT, "
            + "createdAT DATE, "
            + "repost_count INT, "
            + "byte_file_path VARCHAR(1000), "
            + "repostId VARCHAR(1000)"
            + ")";

    private final String INSERT_REPOST_SQL = "INSERT INTO " + tablePath +
            "(posterID, text, likes, comments_count, createdAT, repost_count, byte_file_path, repostId) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_REPOST_SQL = "UPDATE " + tablePath + " SET " +
            "text = ?, likes = ?, comments_count = ?, createdAT = ?, repost_count = ?, byte_file_path = ?, repostId = ? " +
            "WHERE posterID = ?";

    public RepostDAO() {
        super("reposts");
    }

    @Override
    protected Repost mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Repost(resultSet.getString("posterID"),
                resultSet.getString("text"),
                resultSet.getInt("likes"),
                resultSet.getInt("comments_count"),
                resultSet.getDate("createdAT"),
                resultSet.getInt("repost_count"),
                resultSet.getString("byte_file_path"),
                resultSet.getString("repostId"));
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_REPOST_TABLE_SQL;
    }

    public void saveRepost(Repost repost) throws SQLException {
        saveEntity(repost, INSERT_REPOST_SQL, (ps, r) -> {
            ps.setString(1, r.getUserId());
            ps.setString(2, r.getText());
            ps.setInt(3, r.getLikes());
            ps.setInt(4, r.getComments());
            ps.setDate(5, r.getCreatedAt());
            ps.setInt(6, r.getReposts());
            ps.setString(7, r.getByteFilePath());
            ps.setString(8, r.getRepostId());
        });
    }

    public Repost getRepostByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + tablePath + " WHERE posterID = ?";
        return getEntity(query, email);
    }

    public ArrayList<Repost> getAllReposts() throws SQLException {
        String query = "SELECT * FROM " + tablePath;
        return getAllEntities(query);
    }

    public void updateRepost(Repost repost) throws SQLException {
        updatePost(repost, UPDATE_REPOST_SQL);
    }

    public void deleteRepostByEmail(String email, Date date) throws SQLException {
        String query = "DELETE FROM " + tablePath + " WHERE posterID = ? AND createdAT = ?";
        deletePostByEmail(email, date, query);
    }

    public void deleteAllReposts() throws SQLException {
        String query = "DELETE FROM " + tablePath;
        deleteAllEntities(query);
    }
}
