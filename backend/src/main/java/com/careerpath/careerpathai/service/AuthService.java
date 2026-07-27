package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.AuthRequestDTO;
import com.careerpath.careerpathai.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
}
