package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.Skill;

import java.util.List;

public interface SkillService {

    List<Skill> getAllSkills();

    Skill getSkillById(Integer id);
}
