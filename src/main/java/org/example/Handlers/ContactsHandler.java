package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.JobController;
import org.example.Controller.Controllers.UserController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class ContactsHandler extends Handler{
    public static void handleContact (HttpExchange exchange) throws IOException {
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
                        String contactJson = UserController.getContactInfo(email);
                        response = contactJson == null ? "No such contact found!" : contactJson;
                    } else {
                        response = UserController.getAllContactInfos();
                    }
                    break;
                case "DELETE":
                    if (splitPath.length >= 3){
                        String email = new String(exchange.getRequestBody().readAllBytes());
                        UserController.deleteContact(email);
                    } else {
                        UserController.deleteAllContacts();
                    }
                    response = "success"; // but is it really successful ?
                    break;
                default:
                    String contactJson = new String(exchange.getRequestBody().readAllBytes());
                    switch (method) {
                        case "POST" :
                            UserController.createContact(contactJson);
                            break;
                        case "PUT" :
                            UserController.updateContacts(contactJson);
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
