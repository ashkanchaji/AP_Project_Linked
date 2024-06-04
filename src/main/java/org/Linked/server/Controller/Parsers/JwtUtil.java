package org.Linked.server.Controller.Parsers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

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
                .setExpiration(new Date(nowMillis + 24 * 60 * 60 * 1000)) // 1 day expiry
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

    public static String createToken(String email, String password){
        String subject = email.concat(":" + password);
        return generateToken(subject);
    }

    public static boolean isTokenExpired(String jwt) {
        try {
            Claims claims = parseToken(jwt);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            // Token has expired
            return true;
        } catch (Exception e) {
            // Token parsing error or other exceptions
            System.out.println("Error checking token expiration: " + e.getMessage());
            return true; // Assume token is expired in case of any error
        }
    }
}
