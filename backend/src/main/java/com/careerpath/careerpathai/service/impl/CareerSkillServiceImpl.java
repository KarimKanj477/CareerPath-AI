package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Career;
import com.careerpath.careerpathai.entity.CareerSkill;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.exception.CareerNotFoundException;
import com.careerpath.careerpathai.exception.SkillNotFoundException;
import com.careerpath.careerpathai.repository.CareerRepository;
import com.careerpath.careerpathai.repository.CareerSkillRepository;
import com.careerpath.careerpathai.repository.SkillRepository;
import com.careerpath.careerpathai.service.CareerSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerSkillServiceImpl implements CareerSkillService {

    private final CareerSkillRepository careerSkillRepository;
    private final CareerRepository careerRepository;
    private final SkillRepository skillRepository;

    public CareerSkillServiceImpl(CareerSkillRepository careerSkillRepository,
                                   CareerRepository careerRepository,
                                   SkillRepository skillRepository) {
        this.careerSkillRepository = careerSkillRepository;
        this.careerRepository = careerRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<CareerSkill> getCareerSkills(Integer careerId) {
        return careerSkillRepository.findByCareerId(careerId);
    }

    @Override
    public CareerSkill addCareerSkill(Integer careerId, Integer skillId, String importance) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new CareerNotFoundException("Career with id " + careerId + " was not found."));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill with id " + skillId + " was not found."));

        if (careerSkillRepository.existsByCareerIdAndSkillId(careerId, skillId)) {
            throw new RuntimeException("Skill already mapped to this career.");
        }

        CareerSkill careerSkill = new CareerSkill();
        careerSkill.setCareer(career);
        careerSkill.setSkill(skill);
        careerSkill.setImportance(importance != null ? importance : "MEDIUM");
        return careerSkillRepository.save(careerSkill);
    }

    @Override
    public void removeCareerSkill(Integer careerSkillId) {
        CareerSkill cs = careerSkillRepository.findById(careerSkillId)
                .orElseThrow(() -> new RuntimeException("CareerSkill with id " + careerSkillId + " was not found."));
        careerSkillRepository.delete(cs);
    }
}
