package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.LearningResource;

import java.util.List;

public interface LearningResourceService {
    List<LearningResource> getAllResources();
    List<LearningResource> getResourcesBySkill(Integer skillId);
    List<LearningResource> getResourcesBySkillAndFree(Integer skillId, Boolean isFree);
    LearningResource getResourceById(Integer id);
    LearningResource createResource(LearningResource resource);
    LearningResource updateResource(Integer id, LearningResource updates);
    void deleteResource(Integer id);
}
