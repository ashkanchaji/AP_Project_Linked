package org.Linked.server.Controller.DAO;

import org.Linked.server.Model.PhotoFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class PhotoFileDAO extends GenericDAO<PhotoFile> {

    private final String CREATE_PHOTO_FILE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
            + tablePath + " ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "postId VARCHAR(36) NOT NULL, "
            + "photoFile LONGBLOB NOT NULL"
            + ")";

    private final String INSERT_PHOTO_FILE_SQL = "INSERT INTO " + tablePath
            + "(postId, photoFile) VALUES (?, ?)";

    public PhotoFileDAO() {
        super("photo_files");
    }

    @Override
    protected PhotoFile mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String postId = resultSet.getString("postId");
        byte[] photoBytes = resultSet.getBytes("photoFile");
        String photoFile = Base64.getEncoder().encodeToString(photoBytes);
        return new PhotoFile(postId, photoFile);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_PHOTO_FILE_TABLE_SQL;
    }

    public void savePhotoFile(PhotoFile photoFile) throws SQLException {
        checkTableExistence();
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PHOTO_FILE_SQL)) {
            ps.setString(1, photoFile.getPostId());
            ps.setBytes(2, Base64.getDecoder().decode(photoFile.getPhotoFile()));

            ps.executeUpdate();
        }
    }

    public PhotoFile getPhotoFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "SELECT * FROM " + tablePath + " WHERE postId = ?";
        return getEntity(query, postId);
    }

    public void deletePhotoFileByPostId(String postId) throws SQLException {
        checkTableExistence();
        String query = "DELETE FROM " + tablePath + " WHERE postId = ?";
        deleteEntity(query, postId);
    }
}
