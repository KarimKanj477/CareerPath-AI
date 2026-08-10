package com.careerpath.careerpathai.dto;

public class GenerateRoadmapRequestDTO {

    private Integer careerId;

    public GenerateRoadmapRequestDTO() {
    }

    public GenerateRoadmapRequestDTO(Integer careerId) {
        this.careerId = careerId;
    }

    public Integer getCareerId() {
        return careerId;
    }

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
    }
}