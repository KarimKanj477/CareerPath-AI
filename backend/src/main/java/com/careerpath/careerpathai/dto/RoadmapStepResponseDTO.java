package com.careerpath.careerpathai.dto;

import java.util.List;

public class RoadmapStepResponseDTO {

    private Integer id;
    private Integer skillId;
    private String skillName;
    private String title;
    private String description;
    private Integer stepOrder;
    private String status;
    private List<LearningResourceDTO> resources;

    public RoadmapStepResponseDTO() {
    }

    public RoadmapStepResponseDTO(Integer id, Integer skillId, String skillName, String title, String description,
                                  Integer stepOrder, String status, List<LearningResourceDTO> resources) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.title = title;
        this.description = description;
        this.stepOrder = stepOrder;
        this.status = status;
        this.resources = resources;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LearningResourceDTO> getResources() {
        return resources;
    }

    public void setResources(List<LearningResourceDTO> resources) {
        this.resources = resources;
    }
}