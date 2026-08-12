package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "learning_resources")
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "provider", length = 100)
    private String provider;

    @Column(name = "isFree")
    private Boolean isFree;

    public LearningResource() {
    }

    public LearningResource(
            Skill skill,
            String title,
            String url,
            String type,
            String provider,
            Boolean isFree
    ) {
        this.skill = skill;
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

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
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