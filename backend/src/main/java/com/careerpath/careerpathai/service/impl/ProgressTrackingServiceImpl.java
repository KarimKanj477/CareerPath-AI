package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.ProgressTrackingRequestDTO;
import com.careerpath.careerpathai.dto.ProgressTrackingResponseDTO;
import com.careerpath.careerpathai.entity.ProgressTracking;
import com.careerpath.careerpathai.entity.Roadmap;
import com.careerpath.careerpathai.entity.RoadmapStep;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.RoadmapNotFoundException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.ProgressTrackingRepository;
import com.careerpath.careerpathai.repository.RoadmapRepository;
import com.careerpath.careerpathai.repository.RoadmapStepRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.ProgressTrackingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressTrackingServiceImpl
        implements ProgressTrackingService {

    private final ProgressTrackingRepository progressTrackingRepository;
    private final RoadmapStepRepository roadmapStepRepository;
    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;

    public ProgressTrackingServiceImpl(
            ProgressTrackingRepository progressTrackingRepository,
            RoadmapStepRepository roadmapStepRepository,
            UserRepository userRepository,
             RoadmapRepository roadmapRepository
    ) {
        this.progressTrackingRepository = progressTrackingRepository;
        this.roadmapStepRepository = roadmapStepRepository;
        this.userRepository = userRepository;
        this.roadmapRepository=roadmapRepository;
    }

    @Override
    @Transactional
    public ProgressTrackingResponseDTO updateProgress(
            String userEmail,
            Integer roadmapStepId,
            ProgressTrackingRequestDTO request
    ) {

        User user = getUser(userEmail);

        RoadmapStep roadmapStep =
                getOwnedRoadmapStep(
                        roadmapStepId,
                        user.getId()
                );

        Integer percentage =
                request.getProgressPercentage();

        String status;

        LocalDateTime completionDate = null;

        if (percentage == 0) {
            status = "Not Started";
        } else if (percentage == 100) {
            status = "Completed";
            completionDate = LocalDateTime.now();
        } else {
            status = "In Progress";
        }

        ProgressTracking progress =
                progressTrackingRepository
                        .findByUser_IdAndRoadmapStep_Id(
                                user.getId(),
                                roadmapStepId
                        )
                        .orElse(
                                new ProgressTracking(
                                        user,
                                        roadmapStep,
                                        "Not Started",
                                        null,
                                        0
                                )
                        );

        progress.setProgressPercentage(percentage);
        progress.setStatus(status);
        progress.setCompletionDate(completionDate);

        progress =
                progressTrackingRepository.save(progress);

        /*
         * Keep the roadmap step status synchronized
         * with the progress record.
         */
        roadmapStep.setStatus(status);
        roadmapStepRepository.save(roadmapStep);
        updateRoadmapStatus(roadmapStep.getRoadmap());

        return convertToResponse(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressTrackingResponseDTO getProgressForStep(
            String userEmail,
            Integer roadmapStepId
    ) {

        User user = getUser(userEmail);

        RoadmapStep roadmapStep =
                getOwnedRoadmapStep(
                        roadmapStepId,
                        user.getId()
                );

        ProgressTracking progress =
                progressTrackingRepository
                        .findByUser_IdAndRoadmapStep_Id(
                                user.getId(),
                                roadmapStepId
                        )
                        .orElse(null);

        if (progress == null) {
            return new ProgressTrackingResponseDTO(
                    null,
                    roadmapStep.getId(),
                    roadmapStep.getTitle(),
                    "Not Started",
                    0,
                    null
            );
        }

        return convertToResponse(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressTrackingResponseDTO> getMyProgress(
            String userEmail
    ) {

        User user = getUser(userEmail);

        List<ProgressTracking> progressRecords =
                progressTrackingRepository
                        .findAllByUser_Id(user.getId());

        List<ProgressTrackingResponseDTO> responses =
                new ArrayList<>();

        for (ProgressTracking progress : progressRecords) {
            responses.add(
                    convertToResponse(progress)
            );
        }

        return responses;
    }

    private User getUser(String userEmail) {

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: "
                                        + userEmail
                        )
                );
    }

    private RoadmapStep getOwnedRoadmapStep(
            Integer roadmapStepId,
            Integer userId
    ) {

        RoadmapStep roadmapStep =
                roadmapStepRepository
                        .findById(roadmapStepId)
                        .orElseThrow(() ->
                                new RoadmapNotFoundException(
                                        "Roadmap step not found with id: "
                                                + roadmapStepId
                                )
                        );

        Integer roadmapOwnerId =
                roadmapStep
                        .getRoadmap()
                        .getUser()
                        .getId();

        if (!roadmapOwnerId.equals(userId)) {
            throw new RoadmapNotFoundException(
                    "Roadmap step not found with id: "
                            + roadmapStepId
            );
        }

        return roadmapStep;
    }

    private ProgressTrackingResponseDTO convertToResponse(
            ProgressTracking progress
    ) {

        return new ProgressTrackingResponseDTO(
                progress.getId(),
                progress.getRoadmapStep().getId(),
                progress.getRoadmapStep().getTitle(),
                progress.getStatus(),
                progress.getProgressPercentage(),
                progress.getCompletionDate()
        );
    }

    private void updateRoadmapStatus(Roadmap roadmap) {

        List<RoadmapStep> steps =
                roadmapStepRepository
                        .findAllByRoadmap_IdOrderByStepOrderAsc(
                                roadmap.getId()
                        );

        boolean allCompleted = true;
        boolean anyStarted = false;

        for (RoadmapStep step : steps) {

            String stepStatus = step.getStatus();

            if (!"Completed".equalsIgnoreCase(stepStatus)) {
                allCompleted = false;
            }

            if ("Completed".equalsIgnoreCase(stepStatus)
                    || "In Progress".equalsIgnoreCase(stepStatus)) {
                anyStarted = true;
            }
        }

        if (allCompleted && !steps.isEmpty()) {
            roadmap.setStatus("Completed");
        } else if (anyStarted) {
            roadmap.setStatus("In Progress");
        } else {
            roadmap.setStatus("Not Started");
        }

        roadmapRepository.save(roadmap);
    }
}