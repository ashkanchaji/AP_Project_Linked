package org.Linked.server.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.Linked.client.viewControllers.Utils.PDFFileTypeAdapter;
import org.Linked.server.Controller.Controllers.PDFFileController;
import org.Linked.server.Model.PDFFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PDFFileHandler extends Handler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(PDFFile.class, new PDFFileTypeAdapter()).create();

    @Override
    protected String handleRequest(String method, String path, HttpExchange exchange, AtomicInteger statusCode) throws SQLException, IOException {
        String[] splitPath = path.split("/");
        String response;

        switch (method) {
            case "GET":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    String pdfFileJson = PDFFileController.getPDFFile(postId);
                    response = pdfFileJson == null ? "No such PDF file found!" : pdfFileJson;
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            case "DELETE":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    PDFFileController.deletePDFFile(postId);
                    response = "PDF file deleted successfully";
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            case "POST":
            case "PUT":
                if (splitPath.length >= 3) {
                    String postId = splitPath[splitPath.length - 1];
                    String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    PDFFile pdfFile = gson.fromJson(requestBody, PDFFile.class);
                    byte[] pdfData = pdfFile.getPdfFileBytes();
                    PDFFileController.savePDFFile(postId, pdfData);
                    response = "PDF file saved successfully";
                } else {
                    response = "Invalid request path!";
                    statusCode.set(400);
                }
                break;
            default:
                response = "Unsupported method";
                statusCode.set(405);
                break;
        }
        return response;
    }
}
