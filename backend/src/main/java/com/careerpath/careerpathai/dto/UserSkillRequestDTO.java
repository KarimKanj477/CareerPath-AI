package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserSkillRequestDTO {

    @NotNull(message = "Skill ID is required")
    private Integer skillId;

    @Size(max = 50)
    private String proficiencyLevel;

    public UserSkillRequestDTO() {}

    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }

    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
}
