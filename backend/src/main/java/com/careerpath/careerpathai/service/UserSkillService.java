package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.UserSkill;

import java.util.List;

public interface UserSkillService {
    List<UserSkill> getUserSkills(Integer userId);
    UserSkill addUserSkill(Integer userId, Integer skillId, String proficiencyLevel);
    UserSkill updateUserSkill(Integer userSkillId, String proficiencyLevel, Integer requestingUserId);
    void removeUserSkill(Integer userSkillId, Integer requestingUserId);
}
