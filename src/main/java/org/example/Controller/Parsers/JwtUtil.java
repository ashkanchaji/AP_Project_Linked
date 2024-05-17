package org.example.Controller.Parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.Controller.Exeptions.InvalidPassException;
import org.example.Model.User;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HS256 key generation

    public static String generateToken(String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 3600000)) // 1 hour expiry
                .signWith(key)
                .compact();
    }

    public static Claims parseToken(String jwt) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);

            return jws.getBody();
        } catch (SignatureException e) {
            // handle invalid signature
            System.out.println("Invalid JWT signature");
            return null;
        } catch (Exception e) {
            // handle other parsing errors
            System.out.println("JWT parsing error: " + e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) throws InvalidPassException {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        User user = new User("achaji2563@gmail.com", "2", "3", "4sdhfl67d6f");
//
//        String jwt = generateToken(gson.toJson(user));
//        System.out.println("Generated JWT: " + jwt);
//
//        Claims claims = parseToken(jwt);
//        if (claims != null) {
//            System.out.println("Subject: " + claims.getSubject());
//
//            System.out.println("Issued At: " + claims.getIssuedAt());
//            System.out.println("Expiration: " + claims.getExpiration());
//        }
//    }
}

