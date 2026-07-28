package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapRepository extends JpaRepository<Roadmap, Integer> {
    List<Roadmap> findByUserId(Integer userId);
}
