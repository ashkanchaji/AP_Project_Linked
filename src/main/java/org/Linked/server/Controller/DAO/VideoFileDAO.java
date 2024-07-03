package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.VideoFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class VideoFileDAO extends GenericDAO<VideoFile> {

    private final String CREATE_VIDEO_FILE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "postId VARCHAR(36) NOT NULL, "
            + "videoFile LONGBLOB  NOT NULL"
            + ")";

    private final String INSERT_VIDEO_FILE_SQL = "INSERT INTO " + tablePath
            + "(postId, videoFile) VALUES (?, ?)";

    public VideoFileDAO() {
        super("video_files");
    }

    @Override
    protected VideoFile mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String postId = resultSet.getString("postId");
        byte[] videoBytes = resultSet.getBytes("videoFile");
        String videoFile = Base64.getEncoder().encodeToString(videoBytes);
        return new VideoFile(postId, videoFile);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_VIDEO_FILE_TABLE_SQL;
    }

    public void saveVideoFile(VideoFile videoFile) throws SQLException {
        checkTableExistence();
        try (PreparedStatement ps = connection.prepareStatement(INSERT_VIDEO_FILE_SQL)) {
            ps.setString(1, videoFile.getPostId());
            ps.setBytes(2, Base64.getDecoder().decode(videoFile.getVideoFile()));

            ps.executeUpdate();
        }
    }

    public VideoFile getVideoFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE postId = ?";
        return getEntity(query, postId);
    }

    public void deleteVideoFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE postId = ?";
        deleteEntity(query, postId);
    }
}
