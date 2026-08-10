package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "roadmaps")
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "careerId", nullable = false)
    private Career career;

    @Column(name = "title", length = 150)
    private String title;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public Roadmap() {
    }

    public Roadmap(
            User user,
            Career career,
            String title,
            String status
    ) {
        this.user = user;
        this.career = career;
        this.title = title;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}