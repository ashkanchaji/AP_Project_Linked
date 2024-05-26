package org.example.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.Controller.Controllers.JobController;
import org.example.Controller.Controllers.UserController;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class JobHandler extends Handler{
    public static void handleJob (HttpExchange exchange) throws IOException {
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
                        String jonJson = JobController.getJob(email);
                        response = jonJson == null ? "No such job info found!" : jonJson;
                    } else {
                        response = JobController.getAllJobs();
                    }
                    break;
                case "DELETE":
                    if (splitPath.length >= 3){
                        String email = splitPath[splitPath.length - 1];
                        JobController.deleteJob(email);
                    } else {
                        JobController.deleteAllJobs();
                    }
                    response = "success";
                    break;
                default:
                    String jobJson = new String(exchange.getRequestBody().readAllBytes());
                    switch (method) {
                        case "POST" :
                        case "PUT" :
                            JobController.createJob(jobJson);
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
