package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    List<Certification> findByUserId(Integer userId);
}
