package com.expense.management.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

    @Value("${JWT_SECRET}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000*60*60;   // 1 hour

    private Key signingKey;

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("JWT_SECRET key must be at least 32 bytes after decoding.");
            }
            this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT secret key", e);
        }
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token) // This checks expiration and signature automatically
                    .getBody();

            String usernameFromToken = claims.getSubject(); // Extract username from token
            return usernameFromToken.equals(userDetails.getUsername()); // Ensure correct user

        } catch (ExpiredJwtException e) {
            logger.warning("Token expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            logger.warning("Malformed token: " + e.getMessage());
            return false;
        } catch (SecurityException e) {
            logger.warning("Invalid token signature: " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.warning("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
