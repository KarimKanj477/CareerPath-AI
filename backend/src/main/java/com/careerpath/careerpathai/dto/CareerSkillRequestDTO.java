package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CareerSkillRequestDTO {

    @NotNull(message = "Skill ID is required")
    private Integer skillId;

    @Size(max = 20)
    private String importance = "MEDIUM";

    public CareerSkillRequestDTO() {}

    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }

    public String getImportance() { return importance; }
    public void setImportance(String importance) { this.importance = importance; }
}
