package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.CareerRecommendationResponseDTO;
import com.careerpath.careerpathai.service.RecommendationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@SecurityRequirement(name = "bearerAuth")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService
    ) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<CareerRecommendationResponseDTO>>
    getMyRecommendations(Authentication authentication) {

        List<CareerRecommendationResponseDTO> recommendations = recommendationService.getMyRecommendations
                (authentication.getName());

        return ResponseEntity.ok(recommendations);
    }
}