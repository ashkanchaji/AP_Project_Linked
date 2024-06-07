package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.UserController;

import java.io.IOException;
import java.sql.SQLException;

public class EducationHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
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
            case "PUT":
                String eduJson = readRequestBody(exchange);
                UserController.createEducation(eduJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
