package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.UserController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactsHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String contactJson = UserController.getContactInfo(email);
                    response = contactJson == null ? "No such contact found!" : contactJson;
                } else {
                    response = UserController.getAllContactInfos();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    UserController.deleteContact(email);
                } else {
                    UserController.deleteAllContacts();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String contactJson = readRequestBody(exchange);
                UserController.createContact(contactJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
