package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "career_skills",
        uniqueConstraints = @UniqueConstraint(columnNames = {"careerId", "skillId"}))
public class CareerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerId", nullable = false)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @Column(length = 20)
    private String importance = "MEDIUM";

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public CareerSkill() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Career getCareer() { return career; }
    public void setCareer(Career career) { this.career = career; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

    public String getImportance() { return importance; }
    public void setImportance(String importance) { this.importance = importance; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
