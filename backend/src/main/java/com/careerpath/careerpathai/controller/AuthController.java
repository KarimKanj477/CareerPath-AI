package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.dto.auth.AuthResponse;
import com.careerpath.careerpathai.dto.auth.LoginRequest;
import com.careerpath.careerpathai.dto.auth.RegisterRequest;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse authResponse = authService.register(request);

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Registration successful",
                authResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Login successful",
                authResponse
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getMe(
            @AuthenticationPrincipal User user) {

        String roleName = user.getRole() != null ? user.getRole().getName() : null;

        UserResponseDTO dto = new UserResponseDTO(
                user.getId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getExperienceLevel(),
                roleName, user.getCreatedAt()
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", dto));
    }
}
