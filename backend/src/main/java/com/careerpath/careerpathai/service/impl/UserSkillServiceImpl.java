package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.entity.UserSkill;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.exception.UserNotFoundException;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.repository.UserRepository;
import com.careerpath.careerpathai.repository.UserSkillRepository;
import com.careerpath.careerpathai.service.UserSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillServiceImpl(UserSkillRepository userSkillRepository,
                                 UserRepository userRepository,
                                 SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<UserSkill> getUserSkills(Integer userId) {
        return userSkillRepository.findByUserId(userId);
    }

    @Override
    public UserSkill addUserSkill(Integer userId, Integer skillId, String proficiencyLevel) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " was not found."));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill with id " + skillId + " was not found."));

        if (userSkillRepository.existsByUserIdAndSkillId(userId, skillId)) {
            // Update existing instead of throwing
            UserSkill existing = userSkillRepository.findByUserIdAndSkillId(userId, skillId).get();
            existing.setProficiencyLevel(proficiencyLevel);
            return userSkillRepository.save(existing);
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setProficiencyLevel(proficiencyLevel);
        return userSkillRepository.save(userSkill);
    }

    @Override
    public UserSkill updateUserSkill(Integer userSkillId, String proficiencyLevel, Integer requestingUserId) {
        UserSkill userSkill = userSkillRepository.findById(userSkillId)
                .orElseThrow(() -> new UserNotFoundException("UserSkill with id " + userSkillId + " was not found."));
        if (!userSkill.getUser().getId().equals(requestingUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to modify this skill.");
        }
        userSkill.setProficiencyLevel(proficiencyLevel);
        return userSkillRepository.save(userSkill);
    }

    @Override
    public void removeUserSkill(Integer userSkillId, Integer requestingUserId) {
        UserSkill userSkill = userSkillRepository.findById(userSkillId)
                .orElseThrow(() -> new UserNotFoundException("UserSkill with id " + userSkillId + " was not found."));
        if (!userSkill.getUser().getId().equals(requestingUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to remove this skill.");
        }
        userSkillRepository.delete(userSkill);
    }
}
