package com.careerpath.careerpathai.dto;

public class RecommendationSkillDTO {

    private Integer skillId;
    private String skillName;
    private String importance;

    public RecommendationSkillDTO() {
    }

    public RecommendationSkillDTO(
            Integer skillId,
            String skillName,
            String importance
    ) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.importance = importance;
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

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}