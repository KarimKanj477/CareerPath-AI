package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.Roadmap;
import com.careerpath.careerpathai.entity.RoadmapStep;

import java.util.List;

public interface RoadmapService {
    List<Roadmap> getUserRoadmaps(Integer userId);
    Roadmap getRoadmapById(Integer id, Integer requestingUserId);
    Roadmap createRoadmap(Integer userId, Integer careerId);
    Roadmap updateRoadmapStatus(Integer id, String status, Integer requestingUserId);
    void deleteRoadmap(Integer id, Integer requestingUserId);

    List<RoadmapStep> getRoadmapSteps(Integer roadmapId, Integer requestingUserId);
    RoadmapStep addRoadmapStep(Integer roadmapId, RoadmapStep step, Integer requestingUserId);
    RoadmapStep updateRoadmapStep(Integer stepId, RoadmapStep updates, Integer requestingUserId);
    void deleteRoadmapStep(Integer stepId, Integer requestingUserId);
}
