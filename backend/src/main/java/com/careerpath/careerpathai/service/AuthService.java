package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.auth.AuthResponse;
import com.careerpath.careerpathai.dto.auth.LoginRequest;
import com.careerpath.careerpathai.dto.auth.RegisterRequest;
import com.careerpath.careerpathai.entity.User;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    User getAuthenticatedUser(String email);
}
