package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class UserSkillResponseDTO {

    private Integer id;
    private Integer skillId;
    private String skillName;
    private String skillDescription;
    private String skillCategory;
    private String level;
    private LocalDateTime createdAt;

    public UserSkillResponseDTO() {
    }

    public UserSkillResponseDTO(
            Integer id,
            Integer skillId,
            String skillName,
            String skillDescription,
            String skillCategory,
            String level,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.skillCategory = skillCategory;
        this.level = level;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}