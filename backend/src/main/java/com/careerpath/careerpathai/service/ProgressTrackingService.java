package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.ProgressTracking;

import java.util.List;

public interface ProgressTrackingService {
    List<ProgressTracking> getUserProgress(Integer userId);
    ProgressTracking upsertProgress(Integer userId, Integer roadmapStepId,
                                    String status, Integer progressPercentage,
                                    java.time.LocalDateTime completionDate);
    void deleteProgress(Integer id, Integer requestingUserId);
}
