package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepository
        extends JpaRepository<Roadmap, Integer> {

    List<Roadmap> findAllByUser_Id(Integer userId);

    Optional<Roadmap> findByIdAndUser_Id(Integer id, Integer userId);

    boolean existsByUser_IdAndCareer_Id(Integer userId, Integer careerId);

    Optional<Roadmap> findByUser_IdAndCareer_Id(Integer userId, Integer careerId);
}