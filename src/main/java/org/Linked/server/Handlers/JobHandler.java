package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.JobController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class JobHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String jobJson = JobController.getJob(email);
                    response = jobJson == null ? "No such job info found!" : jobJson;
                } else {
                    response = JobController.getAllJobs();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    JobController.deleteJob(email);
                } else {
                    JobController.deleteAllJobs();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String jobJson = readRequestBody(exchange);
                JobController.createJob(jobJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
