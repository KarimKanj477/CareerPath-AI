package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.ProgressTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressTrackingRepository extends JpaRepository<ProgressTracking, Integer> {
    List<ProgressTracking> findByUserId(Integer userId);
    Optional<ProgressTracking> findByUserIdAndRoadmapStepId(Integer userId, Integer roadmapStepId);
}
