package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.Controller.Controllers.UserController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splitPath = path.split("/");

        switch (method){
            // should handle return specific user and ...
            case "GET":
                try {
                    response = UserController.getUsers();
                } catch (SQLException e) { // handle all sql exceptions
                    throw new RuntimeException(e);
                }
                break;
            case "POST":
                break;
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
                break;
        }

        exchange.sendResponseHeaders(200, response.length());

        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }
}
