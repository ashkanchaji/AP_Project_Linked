package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.UserController;
import java.io.IOException;
import java.sql.SQLException;

public class UserHandler extends Handler {

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");
        String response = "";

        try {
            switch (method) {
                case "GET":
                    if (splitPath.length >= 3) {
                        String email = splitPath[splitPath.length - 1];
                        String userJson = UserController.getUser(email);
                        response = userJson == null ? "No such user found!" : userJson;
                    } else {
                        response = UserController.getUsers();
                    }
                    break;
                case "DELETE":
                    if (splitPath.length >= 3) {
                        String userJson = readRequestBody(exchange);
                        UserController.deleteUser(userJson);
                    } else {
                        UserController.deleteUsers();
                    }
                    response = "success";
                    break;
                case "POST":
                case "PUT":
                    String userJson = readRequestBody(exchange);
                    System.out.println(userJson);
                    if ("POST".equals(method)) {
                        UserController.createUser(userJson);
                    } else {
                        UserController.updateUser(userJson);
                    }
                    response = "success";
                    break;
                default:
                    response = "Unsupported method";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Internal Server Error";
        }

        return response;
    }
}
