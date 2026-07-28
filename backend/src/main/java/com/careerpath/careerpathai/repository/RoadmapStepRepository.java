package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.RoadmapStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapStepRepository extends JpaRepository<RoadmapStep, Integer> {
    List<RoadmapStep> findByRoadmapIdOrderByStepOrderAsc(Integer roadmapId);
}
