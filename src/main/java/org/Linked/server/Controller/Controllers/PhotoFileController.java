package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.DAO.PhotoFileDAO;
import org.Linked.server.Model.PhotoFile;

import java.sql.SQLException;
import java.util.Base64;

public class PhotoFileController extends Controller {

    private static final PhotoFileDAO photoFileDAO = new PhotoFileDAO();

    public static void savePhotoFile(String postId, byte[] photoFile) throws SQLException {
        PhotoFile photoFileObj = new PhotoFile(postId, Base64.getEncoder().encodeToString(photoFile));
        photoFileDAO.savePhotoFile(photoFileObj);
    }

    public static String getPhotoFile(String postId) throws SQLException {
        PhotoFile photoFile = photoFileDAO.getPhotoFileByPostId(postId);
        return photoFile == null ? null : gson.toJson(photoFile);
    }

    public static void deletePhotoFile(String postId) throws SQLException {
        photoFileDAO.deletePhotoFileByPostId(postId);
    }
}
