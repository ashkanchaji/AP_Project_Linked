package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.ErrorController;
import org.example.Controller.Controllers.UserController;

import java.io.*;
import java.sql.SQLException;

public class UserHandler extends Handler {

    public static void handleUser(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitPath = path.split("/");

        try {
            switch (method){
                // should handle return specific user and ...
                case "GET":
                    if (splitPath.length >= 3){
                        String email = splitPath[splitPath.length - 1];
                        String userJson = UserController.getUser(email);
                        response = userJson == null ? "No such user found!" : userJson;
                    } else {
                        response = UserController.getUsers();
                    }
                    break;
                case "DELETE":
                    if (splitPath.length >= 3){
                        String userJson = new String(exchange.getRequestBody().readAllBytes());
                        UserController.deleteUser(userJson);
                    } else {
                        UserController.deleteUsers();
                    }
                    response = "success"; // but is it really successful ?
                    break;
                default:
                    String userJson = new String(exchange.getRequestBody().readAllBytes());
                    switch (method) {
                        case "POST" :
                            UserController.createUser(userJson);
                            break;
                        case "PUT" :
                            UserController.updateUser(userJson);
                            break;
                    }
                    response = "success";
                    break;
            }
        } catch (SQLException e) { // handle all sql exceptions
            e.printStackTrace();
        }

        exchange.sendResponseHeaders(200, response.length());

        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }
}
