package com.demo.repositories;

import com.demo.models.entities.QuizHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 165139
 */
@Repository
public interface QuizHistoryRepository extends JpaRepository<QuizHistoryEntity, Integer> {
    @Query("SELECT 1 FROM QuizHistoryEntity where user.userId = ?1 and quiz.quizId = ?2")
    Optional<Object> isExistUserAndQuiz(int userId, int quizId);
}
