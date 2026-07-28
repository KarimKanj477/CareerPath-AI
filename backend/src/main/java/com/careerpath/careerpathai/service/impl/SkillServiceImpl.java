package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.exception.SkillAlreadyExistsException;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill getSkillById(Integer id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(
                        "Skill with id " + id + " was not found."
                ));
    }

    @Override
    public Skill createSkill(Skill skill) {

        if (skillRepository.existsByName(skill.getName())) {
            throw new SkillAlreadyExistsException(
                    "Skill " + skill.getName() + " already exists."
            );
        }

        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Integer id, Skill skill) {

        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(
                        "Skill with id " + id + " was not found."
                ));

        if (skillRepository.existsByNameAndIdNot(skill.getName(), id)) {
            throw new SkillAlreadyExistsException(
                    "Another skill with name " + skill.getName() + " already exists."
            );
        }

        existingSkill.setName(skill.getName());
        existingSkill.setDescription(skill.getDescription());
        existingSkill.setCategory(skill.getCategory());

        return skillRepository.save(existingSkill);
    }

    @Override
    public void deleteSkill(Integer id) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(
                        "Skill with id " + id + " was not found."
                ));

        skillRepository.delete(skill);
    }

    @Override
    public List<Skill> searchSkillsByName(String name) {
        return skillRepository.findByNameContainingIgnoreCase(name);
    }
}
