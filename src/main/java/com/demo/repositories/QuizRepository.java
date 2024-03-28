package com.demo.repositories;

import com.demo.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 165139
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
