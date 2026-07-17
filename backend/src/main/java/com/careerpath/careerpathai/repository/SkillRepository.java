package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill,Integer> {

    List<Skill> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
