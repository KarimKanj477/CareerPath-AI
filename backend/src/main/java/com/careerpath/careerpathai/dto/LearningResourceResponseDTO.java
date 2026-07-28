package com.careerpath.careerpathai.dto;

public class LearningResourceResponseDTO {

    private Integer id;
    private Integer skillId;
    private String skillName;
    private String title;
    private String url;
    private String type;
    private String provider;
    private Boolean isFree;

    public LearningResourceResponseDTO() {}

    public LearningResourceResponseDTO(Integer id, Integer skillId, String skillName,
                                        String title, String url, String type,
                                        String provider, Boolean isFree) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.title = title;
        this.url = url;
        this.type = type;
        this.provider = provider;
        this.isFree = isFree;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSkillId() { return skillId; }
    public void setSkillId(Integer skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
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
