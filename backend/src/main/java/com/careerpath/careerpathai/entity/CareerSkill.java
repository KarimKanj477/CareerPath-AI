package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "career_skills",
        uniqueConstraints = {@UniqueConstraint(name = "uk_career_skill", columnNames = {"careerId", "skillId"})}
)
public class CareerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "careerId", nullable = false)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @Column(name = "importance", length = 20)
    private String importance;

    public CareerSkill() {
    }

    public CareerSkill(
            Career career,
            Skill skill,
            String importance
    ) {
        this.career = career;
        this.skill = skill;
        this.importance = importance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}