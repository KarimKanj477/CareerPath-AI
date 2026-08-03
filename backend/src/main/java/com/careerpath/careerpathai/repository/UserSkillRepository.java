package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository
        extends JpaRepository<UserSkill, Integer> {

    List<UserSkill> findAllByUser_Id(Integer userId);

    Optional<UserSkill> findByIdAndUser_Id(Integer id, Integer userId);

    boolean existsByUser_IdAndSkill_Id(Integer userId, Integer skillId);
}