package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.ProgressTrackingRequestDTO;
import com.careerpath.careerpathai.dto.ProgressTrackingResponseDTO;

import java.util.List;

public interface ProgressTrackingService {

    ProgressTrackingResponseDTO updateProgress(
            String userEmail,
            Integer roadmapStepId,
            ProgressTrackingRequestDTO request
    );

    ProgressTrackingResponseDTO getProgressForStep(
            String userEmail,
            Integer roadmapStepId
    );

    List<ProgressTrackingResponseDTO> getMyProgress(
            String userEmail
    );
}