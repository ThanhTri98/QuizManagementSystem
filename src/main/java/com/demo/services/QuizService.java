package com.demo.services;

import com.demo.commons.mapper.OptionMapper;
import com.demo.commons.mapper.QuestionMapper;
import com.demo.commons.mapper.QuizMapper;
import com.demo.models.dtos.OptionDTO;
import com.demo.models.dtos.QuestionDTO;
import com.demo.models.dtos.QuizDTO;
import com.demo.models.entities.QuizEntity;
import com.demo.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    @Transactional(rollbackOn = Throwable.class)
    public void add(QuizDTO quizDTO) {
        if (isValidQuiz(quizDTO)) {
            var quizEntity = QuizMapper.toEntity(quizDTO);

            var questionEntities = quizDTO.getQuestions().stream()
                    .map(questionDTO -> {
                        var questionEntity = QuestionMapper.toEntity(questionDTO);
                        questionEntity.setQuiz(quizEntity);

                        var optionEntities = questionDTO.getOptions().stream()
                                .map(optionDTO -> {
                                    var optionEntity = OptionMapper.toEntity(optionDTO);
                                    optionEntity.setQuestion(questionEntity);
                                    return optionEntity;
                                }).toList();
                        questionEntity.setOptions(optionEntities);

                        return questionEntity;
                    }).toList();

            quizEntity.setQuestions(questionEntities);
            this.quizRepository.save(quizEntity);
        }
    }

    private boolean isValidQuiz(QuizDTO quizDTO) {
        if (quizDTO != null) {
            if (!StringUtils.hasText(quizDTO.getQuizName())) {
                throw new IllegalArgumentException("Quiz name can not be null or empty!");
            }

            if (CollectionUtils.isEmpty(quizDTO.getQuestions())) {
                throw new IllegalArgumentException("Questions can not be null or empty!");
            }

            for (QuestionDTO question : quizDTO.getQuestions()) {
                if (!StringUtils.hasText(question.getQuestionText())) {
                    throw new IllegalArgumentException("Question text can not be null or empty!");
                }

                if (CollectionUtils.isEmpty(question.getOptions())) {
                    throw new IllegalArgumentException("Options can not be null or empty!");
                }

                boolean isCorrect = false;
                for (OptionDTO option : question.getOptions()) {
                    if (!StringUtils.hasText(option.getOptionText())) {
                        throw new IllegalArgumentException("Option text can not be null or empty!");
                    }
                    isCorrect = option.isCorrect();
                }

                // There must be at least 1 correct answer
                if (!isCorrect) {
                    throw new IllegalArgumentException("There must be at least 1 correct answer!");
                }
            }
            return true;
        }
        return false;
    }

    public void update(QuizEntity entity) {
        this.quizRepository.save(entity);
    }

    public void deleteById(Integer id) {
        this.quizRepository.deleteById(id);
    }
}
