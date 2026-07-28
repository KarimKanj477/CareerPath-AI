package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatMessageRequestDTO {

    @NotBlank(message = "Message cannot be empty")
    private String message;

    public ChatMessageRequestDTO() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
