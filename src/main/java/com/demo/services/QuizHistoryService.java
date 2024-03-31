package com.demo.services;

import com.demo.models.entities.OptionEntity;
import com.demo.models.entities.QuestionEntity;
import com.demo.models.entities.QuizHistoryDetailEntity;
import com.demo.models.entities.QuizHistoryEntity;
import com.demo.models.request.QuizHistoryRequest;
import com.demo.repositories.QuizHistoryDetailRepository;
import com.demo.repositories.QuizHistoryRepository;
import com.demo.repositories.QuizRepository;
import com.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author 165139
 */
@Service
@Slf4j
public class QuizHistoryService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizHistoryRepository quizHistoryRepository;
    private final QuizHistoryDetailRepository quizHistoryDetailRepository;

    @Autowired
    public QuizHistoryService(QuizRepository quizRepository, UserRepository userRepository,
                              QuizHistoryRepository quizHistoryRepository,
                              QuizHistoryDetailRepository quizHistoryDetailRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizHistoryRepository = quizHistoryRepository;
        this.quizHistoryDetailRepository = quizHistoryDetailRepository;
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean submit(final QuizHistoryRequest quizHistoryRequest) {
        quizHistoryRequest.setCreatedDate(new Date());
        quizHistoryRequest.setCompletionDate(new Date(System.currentTimeMillis() + 600000));
        log.info("QuizHistoryRequest {}", quizHistoryRequest);

        if (this.quizHistoryRepository.isExistUserAndQuiz(quizHistoryRequest.getUserId(), quizHistoryRequest.getQuizId()).isPresent()) {
            throw new IllegalArgumentException("The test was taken by this user!");
        }

        this.userRepository.findById(quizHistoryRequest.getUserId())
                .ifPresentOrElse(userE -> {
                    this.quizRepository.findById(quizHistoryRequest.getQuizId())
                            .ifPresentOrElse(quizE -> {
                                final QuizHistoryEntity quizHistoryTmp = new QuizHistoryEntity();
                                quizHistoryTmp.setUser(userE);
                                quizHistoryTmp.setQuiz(quizE);
                                quizHistoryTmp.setCreatedDate(quizHistoryRequest.getCreatedDate());
                                quizHistoryTmp.setCompletionTime(quizHistoryRequest.getCompletionDate());
                                var quizHistory = this.quizHistoryRepository.save(quizHistoryTmp);

                                // Số lượng câu hỏi của bài kiểm tra
                                final double scorePerQuestion = 10d / quizE.getQuestions().size();
                                AtomicReference<Double> currentSore = new AtomicReference<>(0d);
                                final var questionMap = quizE.getQuestions().stream().collect(Collectors.toMap(
                                        QuestionEntity::getQuestionId, v -> v));

                                if (!CollectionUtils.isEmpty(quizHistoryRequest.getUserAnswer())) {
                                    var entities = quizHistoryRequest.getUserAnswer().entrySet().stream()
                                            .map(e -> {
                                                // Danh sách đáp án đã được người dùng chọn
                                                var optionIds = e.getValue();
                                                List<QuizHistoryDetailEntity> quizHistoryDetailEntities = new ArrayList<>();
                                                if (!CollectionUtils.isEmpty(optionIds)) {
                                                    final var questionId = e.getKey();
                                                    final var question = questionMap.get(questionId);

                                                    var optionSelectedList = question.getOptions().stream()
                                                            .filter(v -> optionIds.contains(v.getOptionId())).toList();

                                                    if (!CollectionUtils.isEmpty(optionSelectedList)) {
                                                        for (OptionEntity option : optionSelectedList) {
                                                            final QuizHistoryDetailEntity quizHistoryDetailEntity = new QuizHistoryDetailEntity();
                                                            quizHistoryDetailEntity.setQuizHistory(quizHistory);
                                                            quizHistoryDetailEntity.setCreatedDate(quizHistory.getCreatedDate());
                                                            quizHistoryDetailEntity.setQuestion(question);
                                                            quizHistoryDetailEntity.setOption(option);
                                                            quizHistoryDetailEntities.add(quizHistoryDetailEntity);
                                                        }

                                                        if (isCorrect(optionSelectedList, question.getOptions())) {
                                                            currentSore.updateAndGet(v -> v + scorePerQuestion);
                                                        }
                                                    } else {
                                                        log.info("Options {} not include in Question {}", optionIds, questionId);
                                                    }
                                                }

                                                return quizHistoryDetailEntities;
                                            })
                                            .filter(lst -> !CollectionUtils.isEmpty(lst))
                                            .flatMap(Collection::stream)
                                            .toList();
                                    quizHistory.setScore(Math.round(currentSore.get() * 10) / 10d);

                                    this.quizHistoryDetailRepository.saveAll(entities);
                                } else {
                                    quizHistory.setScore(0d);
                                }
                            }, () -> {
                                throw new IllegalArgumentException("Quiz not found!");
                            });
                }, () -> {
                    throw new IllegalArgumentException("User not found!");
                });
        return true;
    }


    /**
     * @param optionSelectedList Đáp án người dùng chọn
     * @param optionEntityList   Đáp án đúng của hệ thống
     */
    private boolean isCorrect(List<OptionEntity> optionSelectedList, List<OptionEntity> optionEntityList) {
        for (OptionEntity optionEntity : optionSelectedList) {
            for (OptionEntity entity : optionEntityList) {
                if (entity.getOptionId() == optionEntity.getOptionId() && !entity.isCorrect()) {
                    return false;
                }
            }
        }
        return true;
    }

}
