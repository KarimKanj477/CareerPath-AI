package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.RegisterRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {

        UserResponseDTO registeredUser =
                authService.register(requestDTO);

        ApiResponse<UserResponseDTO> response =
                new ApiResponse<>(true, "Registration completed successfully.", registeredUser
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}