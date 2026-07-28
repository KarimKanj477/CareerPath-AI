package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ProgressTrackingRequestDTO {

    @NotNull(message = "Roadmap step ID is required")
    private Integer roadmapStepId;

    @Size(max = 50)
    private String status;

    private LocalDateTime completionDate;

    @Min(0) @Max(100)
    private Integer progressPercentage;

    public ProgressTrackingRequestDTO() {}

    public Integer getRoadmapStepId() { return roadmapStepId; }
    public void setRoadmapStepId(Integer roadmapStepId) { this.roadmapStepId = roadmapStepId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }
}
