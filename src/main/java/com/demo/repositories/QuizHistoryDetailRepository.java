package com.demo.repositories;

import com.demo.models.entities.QuizHistoryDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 165139
 */
@Repository
public interface QuizHistoryDetailRepository extends JpaRepository<QuizHistoryDetailEntity, Integer> {
    List<QuizHistoryDetailEntity> findQuizHistoryDetailEntitiesByQuizHistoryQhId(int qhId);
}
