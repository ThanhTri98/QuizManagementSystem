package com.demo.repositories;

import com.demo.models.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 165139
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
}
