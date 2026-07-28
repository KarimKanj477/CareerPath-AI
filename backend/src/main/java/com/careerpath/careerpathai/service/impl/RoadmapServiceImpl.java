package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Career;
import com.careerpath.careerpathai.entity.Roadmap;
import com.careerpath.careerpathai.entity.RoadmapStep;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.CareerNotFoundException;
import com.careerpath.careerpathai.exception.RoadmapNotFoundException;
import com.careerpath.careerpathai.exception.RoadmapStepNotFoundException;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.repository.CareerRepository;
import com.careerpath.careerpathai.repository.RoadmapRepository;
import com.careerpath.careerpathai.repository.RoadmapStepRepository;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.service.RoadmapService;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapServiceImpl implements RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapStepRepository roadmapStepRepository;
    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final SkillRepository skillRepository;

    public RoadmapServiceImpl(RoadmapRepository roadmapRepository,
                               RoadmapStepRepository roadmapStepRepository,
                               UserRepository userRepository,
                               CareerRepository careerRepository,
                               SkillRepository skillRepository) {
        this.roadmapRepository = roadmapRepository;
        this.roadmapStepRepository = roadmapStepRepository;
        this.userRepository = userRepository;
        this.careerRepository = careerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Roadmap> getUserRoadmaps(Integer userId) {
        return roadmapRepository.findByUserId(userId);
    }

    @Override
    public Roadmap getRoadmapById(Integer id, Integer requestingUserId) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new RoadmapNotFoundException("Roadmap with id " + id + " was not found."));
        checkRoadmapOwnership(roadmap, requestingUserId);
        return roadmap;
    }

    @Override
    public Roadmap createRoadmap(Integer userId, Integer careerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " was not found."));
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new CareerNotFoundException("Career with id " + careerId + " was not found."));

        Roadmap roadmap = new Roadmap();
        roadmap.setUser(user);
        roadmap.setCareer(career);
        roadmap.setStatus("IN_PROGRESS");
        return roadmapRepository.save(roadmap);
    }

    @Override
    public Roadmap updateRoadmapStatus(Integer id, String status, Integer requestingUserId) {
        Roadmap roadmap = getRoadmapById(id, requestingUserId);
        roadmap.setStatus(status);
        return roadmapRepository.save(roadmap);
    }

    @Override
    public void deleteRoadmap(Integer id, Integer requestingUserId) {
        Roadmap roadmap = getRoadmapById(id, requestingUserId);
        roadmapRepository.delete(roadmap);
    }

    @Override
    public List<RoadmapStep> getRoadmapSteps(Integer roadmapId, Integer requestingUserId) {
        Roadmap roadmap = getRoadmapById(roadmapId, requestingUserId);
        return roadmapStepRepository.findByRoadmapIdOrderByStepOrderAsc(roadmapId);
    }

    @Override
    public RoadmapStep addRoadmapStep(Integer roadmapId, RoadmapStep step, Integer requestingUserId) {
        Roadmap roadmap = getRoadmapById(roadmapId, requestingUserId);
        step.setRoadmap(roadmap);

        if (step.getSkill() != null && step.getSkill().getId() != null) {
            Skill skill = skillRepository.findById(step.getSkill().getId()).orElse(null);
            step.setSkill(skill);
        }

        return roadmapStepRepository.save(step);
    }

    @Override
    public RoadmapStep updateRoadmapStep(Integer stepId, RoadmapStep updates, Integer requestingUserId) {
        RoadmapStep step = roadmapStepRepository.findById(stepId)
                .orElseThrow(() -> new RoadmapStepNotFoundException("RoadmapStep with id " + stepId + " was not found."));
        checkRoadmapOwnership(step.getRoadmap(), requestingUserId);

        if (updates.getTitle() != null) step.setTitle(updates.getTitle());
        if (updates.getDescription() != null) step.setDescription(updates.getDescription());
        if (updates.getStepOrder() != null) step.setStepOrder(updates.getStepOrder());
        if (updates.getStatus() != null) step.setStatus(updates.getStatus());
        if (updates.getSkill() != null && updates.getSkill().getId() != null) {
            Skill skill = skillRepository.findById(updates.getSkill().getId()).orElse(null);
            step.setSkill(skill);
        }

        return roadmapStepRepository.save(step);
    }

    @Override
    public void deleteRoadmapStep(Integer stepId, Integer requestingUserId) {
        RoadmapStep step = roadmapStepRepository.findById(stepId)
                .orElseThrow(() -> new RoadmapStepNotFoundException("RoadmapStep with id " + stepId + " was not found."));
        checkRoadmapOwnership(step.getRoadmap(), requestingUserId);
        roadmapStepRepository.delete(step);
    }

    private void checkRoadmapOwnership(Roadmap roadmap, Integer requestingUserId) {
        if (!roadmap.getUser().getId().equals(requestingUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to access this roadmap.");
        }
    }
}
