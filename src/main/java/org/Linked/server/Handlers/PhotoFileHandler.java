package org.Linked.server.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.Linked.client.viewControllers.Utils.PhotoFileTypeAdapter;
import org.Linked.server.Controller.Controllers.PhotoFileController;
import org.Linked.server.Model.PhotoFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PhotoFileHandler extends Handler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(PhotoFile.class, new PhotoFileTypeAdapter()).create();

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");
        String response;

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    String photoFileJson = PhotoFileController.getPhotoFile(postId);
                    response = photoFileJson == null ? "No such photo file found!" : photoFileJson;
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    PhotoFileController.deletePhotoFile(postId);
                    response = "Photo file deleted successfully";
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
                    PhotoFile photoFile = gson.fromJson(requestBody, PhotoFile.class);
                    byte[] photoData = photoFile.getPhotoFileBytes();
                    PhotoFileController.savePhotoFile(postId, photoData);
                    response = "Photo file saved successfully";
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
