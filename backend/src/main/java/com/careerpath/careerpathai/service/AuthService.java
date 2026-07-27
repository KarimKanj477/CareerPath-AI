package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.AuthResponseDTO;
import com.careerpath.careerpathai.dto.LoginRequestDTO;
import com.careerpath.careerpathai.dto.RegisterRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(RegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);
}
