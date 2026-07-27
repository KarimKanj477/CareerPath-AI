package com.careerpath.careerpathai.security;

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
}