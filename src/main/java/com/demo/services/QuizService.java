package com.demo.services;

import com.demo.models.Quiz;
import com.demo.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 165139
 */
@Service
public class QuizService implements IBaseService<Quiz, Integer> {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Quiz> findAll() {
        return this.quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findById(Integer id) {
        return this.quizRepository.findById(id);
    }

    @Override
    public void add(Quiz entity) {
        this.quizRepository.save(entity);
    }

    @Override
    public void update(Quiz entity) {
        this.quizRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        this.quizRepository.deleteById(id);
    }
}
