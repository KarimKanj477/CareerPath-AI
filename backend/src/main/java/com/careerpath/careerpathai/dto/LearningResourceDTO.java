package com.careerpath.careerpathai.dto;

public class LearningResourceDTO {
    private Integer id;
    private String title;
    private String url;
    private String type;
    private String provider;
    private Boolean isFree;

    public LearningResourceDTO() {
    }

    public LearningResourceDTO(Integer id, String title, String url, String type, String provider, Boolean isFree) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.type = type;
        this.provider = provider;
        this.isFree = isFree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }
}