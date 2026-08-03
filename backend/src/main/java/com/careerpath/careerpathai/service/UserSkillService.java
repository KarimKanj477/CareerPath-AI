package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.UserSkillRequestDTO;
import com.careerpath.careerpathai.dto.UserSkillResponseDTO;

import java.util.List;

public interface UserSkillService {

    List<UserSkillResponseDTO> getMySkills(String userEmail);

    UserSkillResponseDTO addSkill(String userEmail, UserSkillRequestDTO request);

    UserSkillResponseDTO updateSkill(Integer userSkillId, String userEmail, UserSkillRequestDTO request);

    void deleteSkill(Integer userSkillId, String userEmail);
}