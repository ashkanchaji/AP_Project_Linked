package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.ConnectController;
import org.Linked.server.Controller.Controllers.FollowController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectHandler extends Handler {

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length == 3) {
                    String email = splitPath[splitPath.length - 1];
                    String connectJson = ConnectController.getConnect(email);
                    response = connectJson == null ? "No such job info found!" : connectJson;
                } else if (splitPath.length == 4) {
                    String senderEmail = splitPath[splitPath.length - 2];
                    String receiverEmail = splitPath[splitPath.length - 1];

                    String connectJson = ConnectController.getConnect(senderEmail, receiverEmail);
                    response = connectJson.equals("null") ? "No such follow info found!" : connectJson;
                }
                else{
                    response = ConnectController.getAllConnects();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String senderEmail = splitPath[splitPath.length - 2];
                    String receiverEmail = splitPath[splitPath.length - 1];
                    ConnectController.deleteConnect(senderEmail , receiverEmail);

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
