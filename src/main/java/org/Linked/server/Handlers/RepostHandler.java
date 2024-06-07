package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.PostController;

import java.io.IOException;
import java.sql.SQLException;

public class RepostHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String repostJson = PostController.getRepost(email);
                    response = repostJson == null ? "No such repost found!" : repostJson;
                } else {
                    response = PostController.getAllReposts();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String repostJson = new String(exchange.getRequestBody().readAllBytes());
                    PostController.deleteRepost(repostJson);
                } else {
                    PostController.deleteAllReposts();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String repostJson = readRequestBody(exchange);
                PostController.createRepost(repostJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
