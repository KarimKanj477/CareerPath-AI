package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {
    List<UserSkill> findByUserId(Integer userId);
    Optional<UserSkill> findByUserIdAndSkillId(Integer userId, Integer skillId);
    boolean existsByUserIdAndSkillId(Integer userId, Integer skillId);
}
