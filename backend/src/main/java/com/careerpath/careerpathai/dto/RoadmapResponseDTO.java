package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RoadmapResponseDTO {

    private Integer id;
    private Integer careerId;
    private String careerTitle;
    private String title;
    private String status;
    private LocalDateTime createdAt;
    private List<RoadmapStepResponseDTO> steps;

    public RoadmapResponseDTO() {
    }

    public RoadmapResponseDTO(
            Integer id,
            Integer careerId,
            String careerTitle,
            String title,
            String status,
            LocalDateTime createdAt,
            List<RoadmapStepResponseDTO> steps
    ) {
        this.id = id;
        this.careerId = careerId;
        this.careerTitle = careerTitle;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCareerId() {
        return careerId;
    }

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
    }

    public String getCareerTitle() {
        return careerTitle;
    }

    public void setCareerTitle(String careerTitle) {
        this.careerTitle = careerTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoadmapStepResponseDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<RoadmapStepResponseDTO> steps) {
        this.steps = steps;
    }
}