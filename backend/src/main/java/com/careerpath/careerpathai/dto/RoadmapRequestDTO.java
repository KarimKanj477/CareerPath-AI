package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotNull;

public class RoadmapRequestDTO {

    @NotNull(message = "Career ID is required")
    private Integer careerId;

    public RoadmapRequestDTO() {}

    public Integer getCareerId() { return careerId; }
    public void setCareerId(Integer careerId) { this.careerId = careerId; }
}
