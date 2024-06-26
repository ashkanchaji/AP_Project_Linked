package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.PostController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PostHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String postJson = PostController.getPost(email);
                    response = postJson == null ? "No such post found!" : postJson;
                } else {
                    response = PostController.getAllPosts();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String postJson = new String(exchange.getRequestBody().readAllBytes());
                    PostController.deletePost(postJson);
                } else {
                    PostController.deleteAllPosts();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String postJson = readRequestBody(exchange);
                PostController.createPost(postJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
