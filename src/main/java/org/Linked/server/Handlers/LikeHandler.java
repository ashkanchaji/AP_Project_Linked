package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.LikeController;

import java.io.IOException;
import java.sql.SQLException;

public class LikeHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String likeJson = LikeController.getLike(email);
                    response = likeJson == null ? "No such job info found!" : likeJson;
                } else {
                    response = LikeController.getAllLikes();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    LikeController.deleteLike(email);
                } else {
                    LikeController.deleteAllLikes();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String likeJson = readRequestBody(exchange);
                LikeController.createLike(likeJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }

}
