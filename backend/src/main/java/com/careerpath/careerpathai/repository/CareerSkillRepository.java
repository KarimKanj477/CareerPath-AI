package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.CareerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerSkillRepository extends JpaRepository<CareerSkill, Integer> {

    List<CareerSkill> findAllByCareer_Id(Integer careerId);
}