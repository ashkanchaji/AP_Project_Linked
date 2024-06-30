package org.Linked.server.Controller.Parsers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final long EXPIRATION_TIME = 3600000; // 1 hour

    // Use the Singleton pattern to ensure the same secret key is reused
    private static Key secretKeyInstance;
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private static Key getSecretKey() {
        if (secretKeyInstance == null) {
            secretKeyInstance = Keys.hmacShaKeyFor(("Ah you think darkness is your ally? You merely adopted the dark." +
                    " I was born in it, molded by it. I didn't see the light until I was already a man," +
                    " by then it was nothing to me but blinding!").getBytes(StandardCharsets.UTF_8));
        }
        return secretKeyInstance;
    }

    public static String generateJwtToken(String username, String password) {
        return Jwts.builder()
                .setSubject(username + ":" + password)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), algorithm)
                .compact();
    }

    public static String getSubjectFromJwt(String jwt) {
        try {
            String[] jwtParts = jwt.split("\\.");

            if (jwtParts.length == 3) {
                String encodedPayload = jwtParts[1];
                byte[] decodedPayload = Base64.getUrlDecoder().decode(encodedPayload);
                String payloadJson = new String(decodedPayload, StandardCharsets.UTF_8);

                // Parse the payload JSON and retrieve the "sub" value
                // Assuming the payload JSON is in the format: {"sub": "username"}
                String sub = payloadJson.substring(payloadJson.indexOf("\"sub\":") + 7);
                sub = sub.substring(0, sub.indexOf("\""));

                return sub;
            } else {
                // Invalid JWT format
                throw new IllegalArgumentException("Invalid JWT format");
            }
        } catch (Exception e) {
            // Handle exception (e.g., invalid JWT format, decoding errors)
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            System.out.println("Token Expired!");;
            return false;
        }
    }

    public static String readJwtTokenFromFile(String filePath) throws IOException {
        // Read the token from the file
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}

