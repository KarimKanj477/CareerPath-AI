package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.ProgressTrackingRequestDTO;
import com.careerpath.careerpathai.dto.ProgressTrackingResponseDTO;
import com.careerpath.careerpathai.entity.ProgressTracking;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.service.ProgressTrackingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressTrackingController {

    private final ProgressTrackingService progressTrackingService;

    public ProgressTrackingController(ProgressTrackingService progressTrackingService) {
        this.progressTrackingService = progressTrackingService;
    }

    private ProgressTrackingResponseDTO toDTO(ProgressTracking p) {
        String stepTitle = p.getRoadmapStep() != null ? p.getRoadmapStep().getTitle() : null;
        return new ProgressTrackingResponseDTO(
                p.getId(), p.getUser().getId(), p.getRoadmapStep().getId(),
                stepTitle, p.getStatus(), p.getCompletionDate(),
                p.getProgressPercentage(), p.getUpdatedAt());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProgressTrackingResponseDTO>>> getMyProgress(
            @AuthenticationPrincipal User principal) {
        List<ProgressTrackingResponseDTO> list = progressTrackingService.getUserProgress(principal.getId())
                .stream().map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Progress retrieved successfully", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProgressTrackingResponseDTO>> upsertProgress(
            @Valid @RequestBody ProgressTrackingRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        ProgressTracking progress = progressTrackingService.upsertProgress(
                principal.getId(),
                dto.getRoadmapStepId(),
                dto.getStatus(),
                dto.getProgressPercentage(),
                dto.getCompletionDate()
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "Progress saved successfully", toDTO(progress)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProgress(
            @PathVariable Integer id,
            @AuthenticationPrincipal User principal) {
        progressTrackingService.deleteProgress(id, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Progress deleted successfully", null));
    }
}
