package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProgressTrackingRequestDTO {

    @NotNull(message = "Progress percentage is required")
    @Min(value = 0, message = "Progress percentage cannot be less than 0")
    @Max(value = 100, message = "Progress percentage cannot be greater than 100")
    private Integer progressPercentage;

    public ProgressTrackingRequestDTO() {
    }

    public ProgressTrackingRequestDTO(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
