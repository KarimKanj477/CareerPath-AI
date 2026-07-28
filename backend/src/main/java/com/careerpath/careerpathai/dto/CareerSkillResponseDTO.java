package com.careerpath.careerpathai.dto;

public class CareerSkillResponseDTO {

    private Integer id;
    private Integer careerId;
    private Integer skillId;
    private String skillName;
    private String skillCategory;
    private String importance;

    public CareerSkillResponseDTO() {}

    public CareerSkillResponseDTO(Integer id, Integer careerId, Integer skillId,
                                   String skillName, String skillCategory,
                                   String importance) {
        this.id = id;
        this.careerId = careerId;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
        this.importance = importance;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCareerId() { return careerId; }
    public void setCareerId(Integer careerId) { this.careerId = careerId; }
    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getSkillCategory() { return skillCategory; }
    public void setSkillCategory(String skillCategory) { this.skillCategory = skillCategory; }
    public String getImportance() { return importance; }
    public void setImportance(String importance) { this.importance = importance; }
}
