package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class ProgressTrackingResponseDTO {

    private Integer id;
    private Integer roadmapStepId;
    private String stepTitle;
    private String status;
    private Integer progressPercentage;
    private LocalDateTime completionDate;

    public ProgressTrackingResponseDTO() {
    }

    public ProgressTrackingResponseDTO(
            Integer id,
            Integer roadmapStepId,
            String stepTitle,
            String status,
            Integer progressPercentage,
            LocalDateTime completionDate
    ) {
        this.id = id;
        this.roadmapStepId = roadmapStepId;
        this.stepTitle = stepTitle;
        this.status = status;
        this.progressPercentage = progressPercentage;
        this.completionDate = completionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}