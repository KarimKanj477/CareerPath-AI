package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class ProgressTrackingResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer roadmapStepId;
    private String stepTitle;
    private String status;
    private LocalDateTime completionDate;
    private Integer progressPercentage;
    private LocalDateTime updatedAt;

    public ProgressTrackingResponseDTO() {
    }

    public ProgressTrackingResponseDTO(Integer id, Integer userId, Integer roadmapStepId, String stepTitle,
                                       String status, LocalDateTime completionDate, Integer progressPercentage,
                                       LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.roadmapStepId = roadmapStepId;
        this.stepTitle = stepTitle;
        this.status = status;
        this.completionDate = completionDate;
        this.progressPercentage = progressPercentage;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoadmapStepId() {
        return roadmapStepId;
    }

    public void setRoadmapStepId(Integer roadmapStepId) {
        this.roadmapStepId = roadmapStepId;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
