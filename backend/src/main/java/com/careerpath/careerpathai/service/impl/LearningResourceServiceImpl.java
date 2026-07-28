package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.LearningResource;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.exception.LearningResourceNotFoundException;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.repository.LearningResourceRepository;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.service.LearningResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningResourceServiceImpl implements LearningResourceService {

    private final LearningResourceRepository learningResourceRepository;
    private final SkillRepository skillRepository;

    public LearningResourceServiceImpl(LearningResourceRepository learningResourceRepository,
                                        SkillRepository skillRepository) {
        this.learningResourceRepository = learningResourceRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<LearningResource> getAllResources() {
        return learningResourceRepository.findAll();
    }

    @Override
    public List<LearningResource> getResourcesBySkill(Integer skillId) {
        return learningResourceRepository.findBySkillId(skillId);
    }

    @Override
    public List<LearningResource> getResourcesBySkillAndFree(Integer skillId, Boolean isFree) {
        return learningResourceRepository.findBySkillIdAndIsFree(skillId, isFree);
    }

    @Override
    public LearningResource getResourceById(Integer id) {
        return learningResourceRepository.findById(id)
                .orElseThrow(() -> new LearningResourceNotFoundException(
                        "Learning resource with id " + id + " was not found."));
    }

    @Override
    public LearningResource createResource(LearningResource resource) {
        Skill skill = skillRepository.findById(resource.getSkill().getId())
                .orElseThrow(() -> new SkillNotFoundException(
                        "Skill with id " + resource.getSkill().getId() + " was not found."));
        resource.setSkill(skill);
        return learningResourceRepository.save(resource);
    }

    @Override
    public LearningResource updateResource(Integer id, LearningResource updates) {
        LearningResource existing = getResourceById(id);

        if (updates.getTitle() != null) existing.setTitle(updates.getTitle());
        if (updates.getUrl() != null) existing.setUrl(updates.getUrl());
        if (updates.getType() != null) existing.setType(updates.getType());
        if (updates.getProvider() != null) existing.setProvider(updates.getProvider());
        if (updates.getIsFree() != null) existing.setIsFree(updates.getIsFree());
        if (updates.getSkill() != null && updates.getSkill().getId() != null) {
            Skill skill = skillRepository.findById(updates.getSkill().getId())
                    .orElseThrow(() -> new SkillNotFoundException(
                            "Skill with id " + updates.getSkill().getId() + " was not found."));
            existing.setSkill(skill);
        }

        return learningResourceRepository.save(existing);
    }

    @Override
    public void deleteResource(Integer id) {
        LearningResource resource = getResourceById(id);
        learningResourceRepository.delete(resource);
    }
}
