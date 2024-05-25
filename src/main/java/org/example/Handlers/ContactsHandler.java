package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.UserController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class ContactsHandler extends Handler{
//    public static void handleContacts(HttpExchange exchange) throws IOException{
//        String method = exchange.getRequestMethod();
//        String path = exchange.getRequestURI().getPath();
//        String response = "";
//        String[] splitPath = path.split("/");
//
//        try {
//            switch (method){
//                // should handle return specific user and ...
//                case "GET":
//                    if (splitPath.length >= 3){
//                        String email = splitPath[splitPath.length - 1];
//                        String contactJson = UserController.getEducation(email);
//                        response = contactJson == null ? "No such education info found!" : contactJson;
//                    } else {
//                        response = UserController.getAllEducations();
//                    }
//                    break;
//                case "DELETE":
//                    if (splitPath.length >= 3){
//                        String email = splitPath[splitPath.length - 1];
//                        UserController.deleteEducation(email);
//                    } else {
//                        UserController.deleteUsers();
//                    }
//                    response = "success";
//                    break;
//                default:
//                    String eduJson = splitPath[splitPath.length - 1];
//                    switch (method) {
//                        case "POST" :
//                            UserController.createUser(eduJson);
//                            break;
//                        case "PUT" :
//                            UserController.updateUser(eduJson);
//                            break;
//                    }
//                    response = "success";
//                    break;
//            }
//        } catch (SQLException e) { // handle all sql exceptions
//            e.printStackTrace();
//        }
//
//        exchange.sendResponseHeaders(200, response.length());
//
//        try (OutputStream stream = exchange.getResponseBody()) {
//            stream.write(response.getBytes());
//        }
//    }
}
