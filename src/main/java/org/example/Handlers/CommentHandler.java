package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.PostController;

import java.io.IOException;
import java.sql.SQLException;

public class CommentHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String commentJson = PostController.getComment(email);
                    response = commentJson == null ? "No such comment found!" : commentJson;
                } else {
                    response = PostController.getAllComments();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String commentJson = new String(exchange.getRequestBody().readAllBytes());
                    PostController.deleteComment(commentJson);
                } else {
                    PostController.deleteAllComments();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String commentJson = readRequestBody(exchange);
                PostController.createComment(commentJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
