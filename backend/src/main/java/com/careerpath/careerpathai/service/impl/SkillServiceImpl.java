package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Skill;
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
                .orElseThrow(() -> new RuntimeException("Skill with id " + id + " was not found."
                        )
                );
    }
}