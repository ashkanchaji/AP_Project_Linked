package org.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.server.Controller.Controllers.FollowController;
import org.server.Controller.Controllers.JobController;
import org.server.Model.Follow;

import java.io.IOException;
import java.sql.SQLException;

public class FollowHandler extends Handler{
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String FollowJson = FollowController.getFollow(email);
                    response = FollowJson == null ? "No such job info found!" : FollowJson;
                } else {
                    response = FollowController.getAllFollows();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    FollowController.deleteFollow(email);
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
