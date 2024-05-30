package org.example.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Handler {
    protected static String readRequestBody(InputStream requestBody) throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line).append("\n"); // Append a newline to separate lines
            }
        }
        return body.toString().trim(); // Trim any leading/trailing whitespaces
    }
}
