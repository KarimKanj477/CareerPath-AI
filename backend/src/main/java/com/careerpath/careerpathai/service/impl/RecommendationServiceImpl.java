package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.CareerRecommendationResponseDTO;
import com.careerpath.careerpathai.dto.RecommendationSkillDTO;
import com.careerpath.careerpathai.entity.Career;
import com.careerpath.careerpathai.entity.CareerSkill;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.entity.UserSkill;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.CareerRepository;
import com.careerpath.careerpathai.repository.CareerSkillRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.repository.UserSkillRepository;
import com.careerpath.careerpathai.service.RecommendationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final CareerRepository careerRepository;
    private final CareerSkillRepository careerSkillRepository;

    public RecommendationServiceImpl(
            UserRepository userRepository,
            UserSkillRepository userSkillRepository,
            CareerRepository careerRepository,
            CareerSkillRepository careerSkillRepository
    ) {
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
        this.careerRepository = careerRepository;
        this.careerSkillRepository = careerSkillRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareerRecommendationResponseDTO> getMyRecommendations(
            String userEmail
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        List<UserSkill> userSkills =
                userSkillRepository.findAllByUser_Id(user.getId());

        Set<Integer> userSkillIds = new HashSet<>();

        for (UserSkill userSkill : userSkills) {
            userSkillIds.add(userSkill.getSkill().getId());
        }

        List<Career> careers = careerRepository.findAll();

        List<CareerRecommendationResponseDTO> recommendations =
                new ArrayList<>();

        for (Career career : careers) {

            List<CareerSkill> requiredSkills =
                    careerSkillRepository.findAllByCareer_Id(
                            career.getId()
                    );

            int totalWeight = 0;
            int matchedWeight = 0;

            List<RecommendationSkillDTO> matchedSkills =
                    new ArrayList<>();

            List<RecommendationSkillDTO> missingSkills =
                    new ArrayList<>();

            for (CareerSkill careerSkill : requiredSkills) {

                Skill skill = careerSkill.getSkill();

                int skillWeight =
                        getImportanceWeight(
                                careerSkill.getImportance()
                        );

                totalWeight += skillWeight;

                RecommendationSkillDTO skillDTO =
                        new RecommendationSkillDTO(
                                skill.getId(),
                                skill.getName(),
                                careerSkill.getImportance()
                        );

                if (userSkillIds.contains(skill.getId())) {
                    matchedWeight += skillWeight;
                    matchedSkills.add(skillDTO);
                } else {
                    missingSkills.add(skillDTO);
                }
            }

            double matchPercentage = 0.0;

            if (totalWeight > 0) {
                matchPercentage =
                        (matchedWeight * 100.0) / totalWeight;

                matchPercentage =
                        Math.round(matchPercentage * 100.0) / 100.0;
            }

            CareerRecommendationResponseDTO recommendation =
                    new CareerRecommendationResponseDTO(
                            career.getId(),
                            career.getTitle(),
                            career.getDescription(),
                            career.getCategory(),
                            career.getAverageSalary(),
                            career.getDemandLevel(),
                            matchPercentage,
                            requiredSkills.size(),
                            matchedSkills,
                            missingSkills
                    );

            recommendations.add(recommendation);
        }

        recommendations.sort(
                Comparator
                        .comparingDouble(
                                CareerRecommendationResponseDTO
                                        ::getMatchPercentage
                        )
                        .reversed()
                        .thenComparing(
                                CareerRecommendationResponseDTO
                                        ::getCareerTitle,
                                String.CASE_INSENSITIVE_ORDER
                        )
        );

        return recommendations;
    }

    private int getImportanceWeight(String importance) {

        if (importance == null) {
            return 2;
        }

        return switch (importance.toUpperCase()) {
            case "HIGH" -> 3;
            case "LOW" -> 1;
            default -> 2;
        };
    }


}