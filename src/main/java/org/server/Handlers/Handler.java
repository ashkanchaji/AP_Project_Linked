package org.server.Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public abstract class Handler {
    protected String response = "";
    protected static String readRequestBody(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes()).trim();
    }

    protected static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }

    protected abstract String handleRequest(String method, String path, HttpExchange exchange) throws SQLException, IOException;

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response;
        int statusCode = 200;

        try {
            response = handleRequest(method, path, exchange);
        } catch (SQLException e) {
            e.printStackTrace();
            response = "Internal Server Error";
            statusCode = 500; // could be handled with the errorController
        }

        sendResponse(exchange, response, statusCode);
    }
}
