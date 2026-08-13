package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "progress_tracking",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_progress_user_step",
                columnNames = {"userId", "roadmapStepId"}
        )
)
public class ProgressTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmapStepId", nullable = false)
    private RoadmapStep roadmapStep;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "completionDate")
    private LocalDateTime completionDate;

    @Column(name = "progressPercentage")
    private Integer progressPercentage;

    public ProgressTracking() {
    }

    public ProgressTracking(
            User user,
            RoadmapStep roadmapStep,
            String status,
            LocalDateTime completionDate,
            Integer progressPercentage
    ) {
        this.user = user;
        this.roadmapStep = roadmapStep;
        this.status = status;
        this.completionDate = completionDate;
        this.progressPercentage = progressPercentage;
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

    public RoadmapStep getRoadmapStep() {
        return roadmapStep;
    }

    public void setRoadmapStep(RoadmapStep roadmapStep) {
        this.roadmapStep = roadmapStep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}