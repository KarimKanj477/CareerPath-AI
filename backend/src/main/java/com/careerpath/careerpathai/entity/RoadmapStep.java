package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roadmap_steps")
public class RoadmapStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmapId", nullable = false)
    private Roadmap roadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId")
    private Skill skill;

    @Column(name = "title", length = 150)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "stepOrder")
    private Integer stepOrder;

    @Column(name = "status", length = 50)
    private String status;

    public RoadmapStep() {
    }

    public RoadmapStep(
            Roadmap roadmap,
            Skill skill,
            String title,
            String description,
            Integer stepOrder,
            String status
    ) {
        this.roadmap = roadmap;
        this.skill = skill;
        this.title = title;
        this.description = description;
        this.stepOrder = stepOrder;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "Not Started";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Roadmap getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(Roadmap roadmap) {
        this.roadmap = roadmap;
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
}