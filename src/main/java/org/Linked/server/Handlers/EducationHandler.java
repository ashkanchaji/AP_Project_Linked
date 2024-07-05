package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.UserController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class EducationHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String eduJson = UserController.getEducation(email);
                    response = eduJson == null ? "No such education info found!" : eduJson;
                } else {
                    response = UserController.getAllEducations();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    UserController.deleteEducation(email);
                } else {
                    UserController.deleteAllEducations();
                }
                response = "success";
                break;
            case "POST":
                String eduJson = readRequestBody(exchange);
                UserController.createEducation(eduJson);
                response = "success";
                break;
            case "PUT":
                String educationJson = readRequestBody(exchange);
                UserController.onlyCreateEducation(educationJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
