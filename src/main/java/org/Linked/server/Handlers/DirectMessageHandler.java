package org.Linked.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import org.Linked.server.Controller.Controllers.DirectMessageController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectMessageHandler extends Handler {
    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String dmId = splitPath[splitPath.length - 1];
                    String dmJson = DirectMessageController.getDirectMessage(dmId);
                    response = dmJson == null ? "No such direct message found!" : dmJson;
                } else {
                    response = DirectMessageController.getAllDirectMessages();
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String dmId = splitPath[splitPath.length - 1];
                    DirectMessageController.deleteDirectMessageById(dmId);
                } else {
                    DirectMessageController.deleteAllDirectMessages();
                }
                response = "success";
                break;
            case "POST":
            case "PUT":
                String dmJson = readRequestBody(exchange);
                DirectMessageController.createDirectMessage(dmJson);
                response = "success";
                break;
            default:
                response = "Unsupported method";
                break;
        }
        return response;
    }
}
