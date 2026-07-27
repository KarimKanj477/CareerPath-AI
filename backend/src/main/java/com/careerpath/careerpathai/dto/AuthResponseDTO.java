package com.careerpath.careerpathai.dto;

public class AuthResponseDTO {
    private UserResponseDTO user;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(UserResponseDTO user) {
        this.user = user;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
