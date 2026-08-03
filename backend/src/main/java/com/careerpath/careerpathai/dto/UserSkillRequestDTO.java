package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserSkillRequestDTO {

    @NotNull(message = "Skill is required.")
    private Integer skillId;

    @NotBlank(message = "Skill level is required.")
    @Size(max = 50, message = "Skill level cannot exceed 50 characters."
    )
    private String level;

    public UserSkillRequestDTO() {
    }

    public UserSkillRequestDTO(Integer skillId, String level) {
        this.skillId = skillId;
        this.level = level;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}