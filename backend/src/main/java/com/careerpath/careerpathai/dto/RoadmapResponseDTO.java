package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class RoadmapResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer careerId;
    private String careerTitle;
    private String status;
    private LocalDateTime createdAt;

    public RoadmapResponseDTO() {}

    public RoadmapResponseDTO(Integer id, Integer userId, Integer careerId,
                               String careerTitle, String status,
                               LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.careerId = careerId;
        this.careerTitle = careerTitle;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getCareerId() { return careerId; }
    public void setCareerId(Integer careerId) { this.careerId = careerId; }
    public String getCareerTitle() { return careerTitle; }
    public void setCareerTitle(String careerTitle) { this.careerTitle = careerTitle; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
