package org.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.server.Controller.Controllers.ConnectController;
import org.server.Controller.Controllers.JobController;
import org.server.Model.Connect;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectHandler extends Handler {

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    String connectJson = ConnectController.getConnect(email);
                    response = connectJson == null ? "No such job info found!" : connectJson;
                } else {
                    response = ConnectController.getAllConnects();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String email = splitPath[splitPath.length - 1];
                    ConnectController.deleteConnect(email);
                } else {
                    ConnectController.deleteAllConnect();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String connectJson = readRequestBody(exchange);
                ConnectController.createConnect(connectJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
