package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LearningResourceRequestDTO {

    @NotNull(message = "Skill ID is required")
    private Integer skillId;

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @Size(max = 255)
    private String url;

    @Size(max = 50)
    private String type;

    @Size(max = 100)
    private String provider;

    private Boolean isFree = true;

    public LearningResourceRequestDTO() {}

    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public Boolean getIsFree() { return isFree; }
    public void setIsFree(Boolean isFree) { this.isFree = isFree; }
}
