package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.UserSkillRequestDTO;
import com.careerpath.careerpathai.dto.UserSkillResponseDTO;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.entity.UserSkill;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.exception.UserSkillAlreadyExistsException;
import com.careerpath.careerpathai.exception.UserSkillNotFoundException;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.repository.UserSkillRepository;
import com.careerpath.careerpathai.service.UserSkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillServiceImpl(
            UserSkillRepository userSkillRepository,
            UserRepository userRepository,
            SkillRepository skillRepository
    ) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<UserSkillResponseDTO> getMySkills(String userEmail) {

        User user = getUserByEmail(userEmail);

        List<UserSkill> userSkills = userSkillRepository.findAllByUser_Id(user.getId());

        List<UserSkillResponseDTO> response = new ArrayList<>();

        for (UserSkill userSkill : userSkills) {
            response.add(mapToResponseDTO(userSkill));
        }

        return response;
    }

    @Override
    public UserSkillResponseDTO addSkill(
            String userEmail,
            UserSkillRequestDTO request
    ) {

        User user = getUserByEmail(userEmail);

        Skill skill = getSkillById(request.getSkillId());

        boolean alreadyExists = userSkillRepository.existsByUser_IdAndSkill_Id(user.getId(), skill.getId());

        if (alreadyExists) {
            throw new UserSkillAlreadyExistsException("This skill has already been added to your profile.");
        }

        UserSkill userSkill = new UserSkill();

        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setLevel(request.getLevel().trim());

        UserSkill savedUserSkill = userSkillRepository.save(userSkill);

        return mapToResponseDTO(savedUserSkill);
    }

    @Override
    public UserSkillResponseDTO updateSkill(
            Integer userSkillId,
            String userEmail,
            UserSkillRequestDTO request
    ) {

        User user = getUserByEmail(userEmail);

        UserSkill existingUserSkill =
                getUserSkillOwnedByUser(userSkillId, user.getId());

        Skill selectedSkill = getSkillById(request.getSkillId());

        boolean skillChanged = !existingUserSkill.getSkill().getId().equals(selectedSkill.getId());

        if (skillChanged &&
                userSkillRepository.existsByUser_IdAndSkill_Id(user.getId(), selectedSkill.getId())) {

            throw new UserSkillAlreadyExistsException("This skill has already been added to your profile.");
        }

        existingUserSkill.setSkill(selectedSkill);
        existingUserSkill.setLevel(request.getLevel().trim());

        UserSkill updatedUserSkill = userSkillRepository.save(existingUserSkill);

        return mapToResponseDTO(updatedUserSkill);
    }

    @Override
    public void deleteSkill(Integer userSkillId, String userEmail) {

        User user = getUserByEmail(userEmail);

        UserSkill userSkill = getUserSkillOwnedByUser(userSkillId, user.getId());

        userSkillRepository.delete(userSkill);
    }

    private User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " was not found."));
    }

    private Skill getSkillById(Integer skillId) {

        return skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill with id " + skillId + " was not found."));
    }

    private UserSkill getUserSkillOwnedByUser(Integer userSkillId, Integer userId) {

        return userSkillRepository
                .findByIdAndUser_Id(userSkillId, userId)
                .orElseThrow(() ->
                        new UserSkillNotFoundException("User skill with id " + userSkillId + " was not found."));
    }

    private UserSkillResponseDTO mapToResponseDTO(
            UserSkill userSkill
    ) {

        Skill skill = userSkill.getSkill();

        return new UserSkillResponseDTO(
                userSkill.getId(),
                skill.getId(),
                skill.getName(),
                skill.getDescription(),
                skill.getCategory(),
                userSkill.getLevel(),
                userSkill.getCreatedAt()
        );
    }
}