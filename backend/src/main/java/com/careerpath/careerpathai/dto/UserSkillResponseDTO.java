package com.careerpath.careerpathai.dto;

public class UserSkillResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer skillId;
    private String skillName;
    private String skillCategory;
    private String proficiencyLevel;

    public UserSkillResponseDTO() {}

    public UserSkillResponseDTO(Integer id, Integer userId, Integer skillId,
                                 String skillName, String skillCategory,
                                 String proficiencyLevel) {
        this.id = id;
        this.userId = userId;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
        this.proficiencyLevel = proficiencyLevel;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getSkillCategory() { return skillCategory; }
    public void setSkillCategory(String skillCategory) { this.skillCategory = skillCategory; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
}
