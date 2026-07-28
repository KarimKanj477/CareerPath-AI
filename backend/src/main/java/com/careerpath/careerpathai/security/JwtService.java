package com.careerpath.careerpathai.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final String jwtSecret;
    private final long jwtExpiration;

    public JwtService(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.expiration}") long jwtExpiration) {

        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String email, String roleName) {

        Date issuedAt = new Date();
        Date expirationDate =
                new Date(issuedAt.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email)
                .claim("role", roleName)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
    public String extractRole(String token) {
        return extractAllClaims(token)
                .get("role", String.class);
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        try {
            Claims claims = extractAllClaims(token);

            String tokenEmail = claims.getSubject();
            Date expiration = claims.getExpiration();

            return expectedEmail.equals(tokenEmail) && expiration.after(new Date());

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

}