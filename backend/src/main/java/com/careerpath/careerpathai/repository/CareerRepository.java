package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CareerRepository extends JpaRepository<Career, Integer> {

    List<Career> findByTitleContainingIgnoreCase(String title);
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdNot(String title, Integer id);

}