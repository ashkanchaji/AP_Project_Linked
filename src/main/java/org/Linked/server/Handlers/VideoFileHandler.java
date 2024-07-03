package org.Linked.server.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.Linked.client.viewControllers.Utils.VideoFileTypeAdapter;
import org.Linked.server.Controller.Controllers.VideoFileController;
import org.Linked.server.Model.VideoFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoFileHandler extends Handler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(VideoFile.class, new VideoFileTypeAdapter()).create();

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");
        String response;

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    String videoFileJson = VideoFileController.getVideoFile(postId);
                    response = videoFileJson == null ? "No such video file found!" : videoFileJson;
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    VideoFileController.deleteVideoFile(postId);
                    response = "Video file deleted successfully";
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            case "POST":
            case "PUT":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    VideoFile videoFile = gson.fromJson(requestBody, VideoFile.class);
                    byte[] videoData = videoFile.getVideoFileBytes();
                    VideoFileController.saveVideoFile(postId, videoData);
                    response = "Video file saved successfully";
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            default:
                response = "Unsupported method";
                statusCode.set(405);
                break;
        }
        return response;
    }
}
