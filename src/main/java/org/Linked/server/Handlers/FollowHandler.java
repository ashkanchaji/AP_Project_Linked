package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.FollowController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class FollowHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length == 3) {
                    String email = splitPath[splitPath.length - 1];
                    String FollowJson = FollowController.getFollow(email);
                    response = FollowJson.equals("null") ? "No such follow info found!" : FollowJson;
                } else if (splitPath.length == 4) {
                    String followerEmail = splitPath[splitPath.length - 2];
                    String followingEmail = splitPath[splitPath.length - 1];

                    String followJson = FollowController.getFollow(followerEmail, followingEmail);
                    response = followJson.equals("null") ? "No such follow info found!" : followJson;
                } else {
                    response = FollowController.getAllFollows();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String followerEmail = splitPath[splitPath.length - 2];
                    String followingEmail = splitPath[splitPath.length - 1];
                    FollowController.deleteFollow(followerEmail, followingEmail);
                } else {
                    FollowController.deleteAllFollows();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String followJson = readRequestBody(exchange);
                FollowController.createFollow(followJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
