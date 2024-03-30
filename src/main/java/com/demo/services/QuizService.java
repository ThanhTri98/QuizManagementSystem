package com.demo.services;

import com.demo.commons.mapper.QuizMapper;
import com.demo.models.dtos.QuizDTO;
import com.demo.models.entities.QuizEntity;
import com.demo.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author 165139
 */
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<QuizDTO> findAll() {
        var lst = this.quizRepository.findAll();
        return CollectionUtils.isEmpty(lst) ? List.of() : QuizMapper.toDTO(lst);
    }

    public Optional<QuizDTO> findById(Integer id) {
        return this.quizRepository.findById(id).map(QuizMapper::toDTO);
    }

    public void add(QuizEntity entity) {
        this.quizRepository.save(entity);
    }

    public void update(QuizEntity entity) {
        this.quizRepository.save(entity);
    }

    public void deleteById(Integer id) {
        this.quizRepository.deleteById(id);
    }
}
