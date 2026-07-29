package com.careerpath.careerpathai.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void shouldGenerateAndValidateTokenWithClaims() {
        JwtUtil jwtUtil = new JwtUtil("test-secret-key-at-least-32-characters-long", 86_400_000L);

        String token = jwtUtil.generateToken("student@example.com", "STUDENT", 42);

        assertNotNull(token);
        assertEquals("student@example.com", jwtUtil.extractEmail(token));
        assertEquals("STUDENT", jwtUtil.extractRole(token));
        assertEquals(42, jwtUtil.extractUserId(token));
        assertTrue(jwtUtil.isTokenValid(token, "student@example.com"));
    }
}
