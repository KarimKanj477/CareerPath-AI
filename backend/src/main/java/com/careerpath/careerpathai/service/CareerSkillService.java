package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.CareerSkill;

import java.util.List;

public interface CareerSkillService {
    List<CareerSkill> getCareerSkills(Integer careerId);
    CareerSkill addCareerSkill(Integer careerId, Integer skillId, String importance);
    void removeCareerSkill(Integer careerSkillId);
}
