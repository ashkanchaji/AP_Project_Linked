package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.UserController;

import java.io.IOException;
import java.sql.SQLException;

public class UserHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

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
                    String userJson = new String(exchange.getRequestBody().readAllBytes());
                    UserController.deleteUser(userJson);
                } else {
                    UserController.deleteUsers();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String userJson = readRequestBody(exchange);
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
        return response;
    }
}
