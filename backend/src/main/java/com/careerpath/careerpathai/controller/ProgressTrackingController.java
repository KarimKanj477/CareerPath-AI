package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ProgressTrackingRequestDTO;
import com.careerpath.careerpathai.dto.ProgressTrackingResponseDTO;
import com.careerpath.careerpathai.service.ProgressTrackingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@SecurityRequirement(name = "bearerAuth")
public class ProgressTrackingController {

    private final ProgressTrackingService progressTrackingService;

    public ProgressTrackingController(
            ProgressTrackingService progressTrackingService
    ) {
        this.progressTrackingService = progressTrackingService;
    }

    @PutMapping("/steps/{roadmapStepId}")
    public ResponseEntity<ProgressTrackingResponseDTO> updateProgress(
            Authentication authentication,
            @PathVariable Integer roadmapStepId,
            @Valid @RequestBody ProgressTrackingRequestDTO request
    ) {

        String userEmail = authentication.getName();

        return ResponseEntity.ok(
                progressTrackingService.updateProgress(
                        userEmail,
                        roadmapStepId,
                        request
                )
        );
    }

    @GetMapping("/steps/{roadmapStepId}")
    public ResponseEntity<ProgressTrackingResponseDTO> getProgressForStep(
            Authentication authentication,
            @PathVariable Integer roadmapStepId
    ) {

        String userEmail = authentication.getName();

        return ResponseEntity.ok(
                progressTrackingService.getProgressForStep(
                        userEmail,
                        roadmapStepId
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProgressTrackingResponseDTO>> getMyProgress(
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        return ResponseEntity.ok(
                progressTrackingService.getMyProgress(
                        userEmail
                )
        );
    }
}