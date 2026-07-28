package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_skills",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "skillId"}))
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @Column(length = 50)
    private String proficiencyLevel;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserSkill() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
