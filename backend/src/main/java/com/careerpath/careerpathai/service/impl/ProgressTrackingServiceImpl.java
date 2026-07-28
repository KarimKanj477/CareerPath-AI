package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.ProgressTracking;
import com.careerpath.careerpathai.entity.RoadmapStep;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.RoadmapStepNotFoundException;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.ProgressTrackingRepository;
import com.careerpath.careerpathai.repository.RoadmapStepRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.ProgressTrackingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {

    private final ProgressTrackingRepository progressTrackingRepository;
    private final UserRepository userRepository;
    private final RoadmapStepRepository roadmapStepRepository;

    public ProgressTrackingServiceImpl(ProgressTrackingRepository progressTrackingRepository,
                                        UserRepository userRepository,
                                        RoadmapStepRepository roadmapStepRepository) {
        this.progressTrackingRepository = progressTrackingRepository;
        this.userRepository = userRepository;
        this.roadmapStepRepository = roadmapStepRepository;
    }

    @Override
    public List<ProgressTracking> getUserProgress(Integer userId) {
        return progressTrackingRepository.findByUserId(userId);
    }

    @Override
    public ProgressTracking upsertProgress(Integer userId, Integer roadmapStepId,
                                            String status, Integer progressPercentage,
                                            LocalDateTime completionDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " was not found."));
        RoadmapStep step = roadmapStepRepository.findById(roadmapStepId)
                .orElseThrow(() -> new RoadmapStepNotFoundException(
                        "RoadmapStep with id " + roadmapStepId + " was not found."));

        ProgressTracking progress = progressTrackingRepository
                .findByUserIdAndRoadmapStepId(userId, roadmapStepId)
                .orElse(new ProgressTracking());

        progress.setUser(user);
        progress.setRoadmapStep(step);
        if (status != null) progress.setStatus(status);
        if (progressPercentage != null) progress.setProgressPercentage(progressPercentage);
        if (completionDate != null) progress.setCompletionDate(completionDate);

        return progressTrackingRepository.save(progress);
    }

    @Override
    public void deleteProgress(Integer id, Integer requestingUserId) {
        ProgressTracking progress = progressTrackingRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Progress entry with id " + id + " was not found."));
        if (!progress.getUser().getId().equals(requestingUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this progress entry.");
        }
        progressTrackingRepository.delete(progress);
    }
}
