package com.careerpath.careerpathai.repository;

import com.careerpath.careerpathai.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Integer> {
    List<ChatHistory> findByUserIdOrderByCreatedAtAsc(Integer userId);
    void deleteByUserId(Integer userId);
}
