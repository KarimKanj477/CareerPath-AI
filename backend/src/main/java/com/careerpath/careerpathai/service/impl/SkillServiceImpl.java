package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.dto.SkillRequestDTO;
import com.careerpath.careerpathai.dto.SkillResponseDTO;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.exception.SkillAlreadyExistsException;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillResponseDTO> getAllSkills() {

        List<Skill> skills = skillRepository.findAll();

        List<SkillResponseDTO> response = new ArrayList<>();

        for (Skill skill : skills) {
            response.add(mapToResponseDTO(skill));
        }

        return response;
    }

    @Override
    public SkillResponseDTO getSkillById(Integer id) {

        Skill skill = getSkillEntityById(id);

        return mapToResponseDTO(skill);
    }

    @Override
    public SkillResponseDTO createSkill(SkillRequestDTO requestDTO) {

        if (skillRepository.existsByName(requestDTO.getName())) {
            throw new SkillAlreadyExistsException(
                    "Skill " + requestDTO.getName() + " already exists."
            );
        }

        Skill skill = mapToEntity(requestDTO);

        Skill savedSkill = skillRepository.save(skill);

        return mapToResponseDTO(savedSkill);
    }

    @Override
    public SkillResponseDTO updateSkill(
            Integer id,
            SkillRequestDTO requestDTO) {

        Skill existingSkill = getSkillEntityById(id);

        if (!existingSkill.getName()
                .equalsIgnoreCase(requestDTO.getName())
                && skillRepository.existsByName(requestDTO.getName())) {

            throw new SkillAlreadyExistsException("Skill " + requestDTO.getName() + " already exists."
            );
        }

        existingSkill.setName(requestDTO.getName());
        existingSkill.setDescription(requestDTO.getDescription());
        existingSkill.setCategory(requestDTO.getCategory());

        Skill updatedSkill = skillRepository.save(existingSkill);

        return mapToResponseDTO(updatedSkill);
    }

    @Override
    public void deleteSkill(Integer id) {

        Skill skill = getSkillEntityById(id);

        skillRepository.delete(skill);
    }

    @Override
    public List<SkillResponseDTO> searchSkillsByName(String name) {

        List<Skill> skills = skillRepository.findByNameContainingIgnoreCase(name);

        List<SkillResponseDTO> response = new ArrayList<>();

        for (Skill skill : skills) {
            response.add(mapToResponseDTO(skill));
        }

        return response;
    }

    private Skill getSkillEntityById(Integer id) {

        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new SkillNotFoundException("Skill with id " + id + " was not found.")
                );
    }

    private Skill mapToEntity(SkillRequestDTO requestDTO) {

        Skill skill = new Skill();

        skill.setName(requestDTO.getName());
        skill.setDescription(requestDTO.getDescription());
        skill.setCategory(requestDTO.getCategory());

        return skill;
    }

    private SkillResponseDTO mapToResponseDTO(Skill skill) {

        return new SkillResponseDTO(skill.getId(), skill.getName(), skill.getDescription(), skill.getCategory());
    }
}