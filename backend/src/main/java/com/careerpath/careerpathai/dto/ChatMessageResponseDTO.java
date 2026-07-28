package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class ChatMessageResponseDTO {

    private Integer id;
    private Integer userId;
    private String sender;
    private String message;
    private LocalDateTime createdAt;

    public ChatMessageResponseDTO() {}

    public ChatMessageResponseDTO(Integer id, Integer userId, String sender,
                                   String message, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
