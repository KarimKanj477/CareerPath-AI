package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningResourceRepository extends JpaRepository<LearningResource,Integer> {
    List<LearningResource> findAllBySkill_Id(Integer skillId);
}
