package org.Linked.client.viewControllers.Utils;

import org.Linked.server.Controller.Parsers.JwtUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JWTController {
    private static String jwtKey;

    public static String getJwtKey() {
        return jwtKey;
    }

    public static void setJwtKey(String jwtKey) {
        JWTController.jwtKey = jwtKey;
    }

    public static void removeJwtKey() {
        JWTController.jwtKey = null;
    }

    public static boolean isExpired(String jwtToken) {
        try {
            return !JwtUtil.validateJwtToken(jwtToken);
        } catch (Exception e) {
            // Handle file reading error or JWT validation error
            System.out.println("Error processing JWT token: " + e.getMessage());
            return true; // Assume token is expired on error
        }
    }

    public static String readJwtTokenFromFile(String filePath) throws IOException {
        // Read the token from the file
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8).trim(); // Trim to remove any extraneous whitespace
    }

    public static String getSubjectFromJwt(String jwt) {
        return JwtUtil.getSubjectFromJwt(jwt);
    }
}
