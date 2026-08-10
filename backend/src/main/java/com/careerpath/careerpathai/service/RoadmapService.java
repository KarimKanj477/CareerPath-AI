package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.RoadmapResponseDTO;

import java.util.List;

public interface RoadmapService {

    RoadmapResponseDTO generateRoadmap(
            String userEmail,
            Integer careerId
    );

    List<RoadmapResponseDTO> getMyRoadmaps(
            String userEmail
    );

    RoadmapResponseDTO getMyRoadmap(
            String userEmail,
            Integer roadmapId
    );
}