package com.studentManagement.Student.Center.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key must be at least 256 bits (32 characters for HS256)
    private final String SECRET_KEY = "my-secret-key-for-jwt-token-123456";

    //  Generate key object from secret string
    //Converts the String secret key into a Key object using hmacShaKeyFor(...).
    //This is required because the signWith(...) and parser() methods expect a Key object, not just a String

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //  Generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hr
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //  Correct usage
                .compact();
    }

    //  Extract username from token
    public String extractUsername(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey()) //  Correct usage
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //  Token validity check
    public boolean isTokenValid(String token) {
        try {
            return !Jwts
                    .parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}