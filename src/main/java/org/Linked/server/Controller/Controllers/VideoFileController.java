package org.Linked.server.Controller.Controllers;

import org.Linked.server.Controller.DAO.VideoFileDAO;
import org.Linked.server.Model.VideoFile;

import java.sql.SQLException;
import java.util.Base64;

public class VideoFileController extends Controller {

    private static final VideoFileDAO videoFileDAO = new VideoFileDAO();

    public static void saveVideoFile(String postId, byte[] videoFile) throws SQLException {
        VideoFile videoFileObj = new VideoFile(postId, Base64.getEncoder().encodeToString(videoFile));
        videoFileDAO.saveVideoFile(videoFileObj);
    }

    public static String getVideoFile(String postId) throws SQLException {
        VideoFile videoFile = videoFileDAO.getVideoFileByPostId(postId);
        return videoFile == null ? null : gson.toJson(videoFile);
    }

    public static void deleteVideoFile(String postId) throws SQLException {
        videoFileDAO.deleteVideoFileByPostId(postId);
    }
}
