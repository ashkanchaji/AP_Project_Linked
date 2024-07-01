package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.EducationSkillsController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class EducationSkillsHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String eduSkillsJson = EducationSkillsController.getEducationSkills(email);
                    response = eduSkillsJson == null ? "No such education skills info found!" : eduSkillsJson;
                } else {
                    response = EducationSkillsController.getAllEducationSkills();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    EducationSkillsController.deleteEducationSkills(email);
                } else {
                    EducationSkillsController.deleteAllEducationSkills();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String eduSkillsJson = readRequestBody(exchange);
                EducationSkillsController.createEducationSkills(eduSkillsJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
