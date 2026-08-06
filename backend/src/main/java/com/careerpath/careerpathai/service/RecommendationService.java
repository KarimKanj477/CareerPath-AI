package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.CareerRecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {
    List<CareerRecommendationResponseDTO> getMyRecommendations(String userEmail);

}
