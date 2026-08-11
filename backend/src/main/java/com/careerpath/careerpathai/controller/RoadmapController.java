package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.GenerateRoadmapRequestDTO;
import com.careerpath.careerpathai.dto.RoadmapResponseDTO;
import com.careerpath.careerpathai.service.RoadmapService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
@SecurityRequirement(name = "bearerAuth")
public class RoadmapController {

    private final RoadmapService roadmapService;

    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @PostMapping("/generate")
    public ResponseEntity<RoadmapResponseDTO> generateRoadmap(
            Authentication authentication,
            @RequestBody GenerateRoadmapRequestDTO request
    ) {

        String userEmail = authentication.getName();

        RoadmapResponseDTO roadmap =
                roadmapService.generateRoadmap(
                        userEmail,
                        request.getCareerId()
                );

        return ResponseEntity.ok(roadmap);
    }

    @GetMapping("/me")
    public ResponseEntity<List<RoadmapResponseDTO>> getMyRoadmaps(
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        return ResponseEntity.ok(
                roadmapService.getMyRoadmaps(userEmail)
        );
    }

    @GetMapping("/{roadmapId}")
    public ResponseEntity<RoadmapResponseDTO> getMyRoadmap(
            Authentication authentication,
            @PathVariable Integer roadmapId
    ) {

        String userEmail = authentication.getName();

        return ResponseEntity.ok(
                roadmapService.getMyRoadmap(
                        userEmail,
                        roadmapId
                )
        );
    }
}