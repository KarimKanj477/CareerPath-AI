package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.RoadmapResponseDTO;
import com.careerpath.careerpathai.dto.RoadmapStepResponseDTO;
import com.careerpath.careerpathai.entity.*;
import com.careerpath.careerpathai.exception.CareerNotFoundException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.*;
import com.careerpath.careerpathai.service.RoadmapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.careerpath.careerpathai.exception.RoadmapNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.careerpath.careerpathai.dto.LearningResourceDTO;

@Service
public class RoadmapServiceImpl implements RoadmapService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final UserSkillRepository userSkillRepository;
    private final CareerSkillRepository careerSkillRepository;
    private final RoadmapRepository roadmapRepository;
    private final RoadmapStepRepository roadmapStepRepository;
    private final LearningResourceRepository learningResourceRepository;

    public RoadmapServiceImpl(
            UserRepository userRepository,
            CareerRepository careerRepository,
            UserSkillRepository userSkillRepository,
            CareerSkillRepository careerSkillRepository,
            RoadmapRepository roadmapRepository,
            RoadmapStepRepository roadmapStepRepository,
            LearningResourceRepository learningResourceRepository
    ) {
        this.userRepository = userRepository;
        this.careerRepository = careerRepository;
        this.userSkillRepository = userSkillRepository;
        this.careerSkillRepository = careerSkillRepository;
        this.roadmapRepository = roadmapRepository;
        this.roadmapStepRepository = roadmapStepRepository;
        this.learningResourceRepository=learningResourceRepository;
    }

    @Override
    @Transactional
    public RoadmapResponseDTO generateRoadmap(
            String userEmail,
            Integer careerId
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        Career career = careerRepository.findById(careerId)
                .orElseThrow(() ->
                        new CareerNotFoundException(
                                "Career not found with id: " + careerId
                        )
                );

        Roadmap existingRoadmap =
                roadmapRepository
                        .findByUser_IdAndCareer_Id(
                                user.getId(),
                                careerId
                        )
                        .orElse(null);

        if (existingRoadmap != null) {
            return convertToResponse(existingRoadmap);
        }

        List<UserSkill> userSkills =
                userSkillRepository.findAllByUser_Id(
                        user.getId()
                );

        Set<Integer> userSkillIds = new HashSet<>();

        for (UserSkill userSkill : userSkills) {
            userSkillIds.add(
                    userSkill.getSkill().getId()
            );
        }

        List<CareerSkill> careerSkills =
                careerSkillRepository.findAllByCareer_Id(
                        careerId
                );

        List<CareerSkill> missingCareerSkills =
                new ArrayList<>();

        for (CareerSkill careerSkill : careerSkills) {

            Integer skillId =
                    careerSkill.getSkill().getId();

            if (!userSkillIds.contains(skillId)) {
                missingCareerSkills.add(careerSkill);
            }
        }

        missingCareerSkills.sort(
                Comparator.comparingInt(
                        (CareerSkill careerSkill) ->
                                getImportanceWeight(
                                        careerSkill.getImportance()
                                )
                ).reversed()
        );

        Roadmap roadmap = new Roadmap(
                user,
                career,
                "Personalized Roadmap - " + career.getTitle(),
                "Not Started"
        );

        roadmap = roadmapRepository.save(roadmap);

        List<RoadmapStep> roadmapSteps =
                new ArrayList<>();

        int stepOrder = 1;

        for (CareerSkill careerSkill : missingCareerSkills) {

            Skill skill = careerSkill.getSkill();

            RoadmapStep step =
                    new RoadmapStep(
                            roadmap,
                            skill,
                            "Learn " + skill.getName(),
                            "Develop the "
                                    + skill.getName()
                                    + " skill required for "
                                    + career.getTitle()
                                    + ".",
                            stepOrder,
                            "Not Started"
                    );

            roadmapSteps.add(step);

            stepOrder++;
        }

        roadmapStepRepository.saveAll(roadmapSteps);

        return convertToResponse(roadmap);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadmapResponseDTO> getMyRoadmaps(
            String userEmail
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: "
                                        + userEmail
                        )
                );

        List<Roadmap> roadmaps =
                roadmapRepository.findAllByUser_Id(
                        user.getId()
                );

        List<RoadmapResponseDTO> responses =
                new ArrayList<>();

        for (Roadmap roadmap : roadmaps) {
            responses.add(
                    convertToResponse(roadmap)
            );
        }

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public RoadmapResponseDTO getMyRoadmap(
            String userEmail,
            Integer roadmapId
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: "
                                        + userEmail
                        )
                );

        Roadmap roadmap =
                roadmapRepository
                        .findByIdAndUser_Id(
                                roadmapId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RoadmapNotFoundException(
                                        "Roadmap not found with id: " +roadmapId
                                )
                        );

        return convertToResponse(roadmap);
    }

    private RoadmapResponseDTO convertToResponse(
            Roadmap roadmap
    ) {

        List<RoadmapStep> steps =
                roadmapStepRepository
                        .findAllByRoadmap_IdOrderByStepOrderAsc(
                                roadmap.getId()
                        );

        List<RoadmapStepResponseDTO> stepDTOs =
                new ArrayList<>();

        for (RoadmapStep step : steps) {

            Integer skillId = null;
            String skillName = null;

            List<LearningResourceDTO> resourceDTOs =
                    new ArrayList<>();

            if (step.getSkill() != null) {

                skillId = step.getSkill().getId();
                skillName = step.getSkill().getName();

                List<LearningResource> resources =
                        learningResourceRepository
                                .findAllBySkill_Id(skillId);

                for (LearningResource resource : resources) {

                    LearningResourceDTO resourceDTO =
                            new LearningResourceDTO(
                                    resource.getId(),
                                    resource.getTitle(),
                                    resource.getUrl(),
                                    resource.getType(),
                                    resource.getProvider(),
                                    resource.getIsFree()
                            );

                    resourceDTOs.add(resourceDTO);
                }
            }

            RoadmapStepResponseDTO stepDTO =
                    new RoadmapStepResponseDTO(
                            step.getId(),
                            skillId,
                            skillName,
                            step.getTitle(),
                            step.getDescription(),
                            step.getStepOrder(),
                            step.getStatus(),
                            resourceDTOs
                    );

            stepDTOs.add(stepDTO);
        }

        return new RoadmapResponseDTO(
                roadmap.getId(),
                roadmap.getCareer().getId(),
                roadmap.getCareer().getTitle(),
                roadmap.getTitle(),
                roadmap.getStatus(),
                roadmap.getCreatedAt(),
                stepDTOs
        );
    }

    private int getImportanceWeight(
            String importance
    ) {

        if (importance == null) {
            return 2;
        }

        return switch (
                importance.toUpperCase()
                ) {
            case "HIGH" -> 3;
            case "LOW" -> 1;
            default -> 2;
        };
    }
}