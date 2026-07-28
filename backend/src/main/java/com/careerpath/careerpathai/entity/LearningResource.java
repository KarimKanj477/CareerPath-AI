package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_resources")
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 255)
    private String url;

    @Column(length = 50)
    private String type;

    @Column(length = 100)
    private String provider;

    @Column(nullable = false)
    private Boolean isFree = true;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public LearningResource() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
}
