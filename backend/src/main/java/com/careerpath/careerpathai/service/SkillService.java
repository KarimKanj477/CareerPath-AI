package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.dto.SkillRequestDTO;
import com.careerpath.careerpathai.dto.SkillResponseDTO;

import java.util.List;

public interface SkillService {

    List<SkillResponseDTO> getAllSkills();

    SkillResponseDTO getSkillById(Integer id);

    SkillResponseDTO createSkill(SkillRequestDTO requestDTO);

    SkillResponseDTO updateSkill(Integer id, SkillRequestDTO requestDTO);

    void deleteSkill(Integer id);

    List<SkillResponseDTO> searchSkillsByName(String name);
}