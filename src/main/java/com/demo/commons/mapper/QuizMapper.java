package com.demo.commons.mapper;

import com.demo.models.dtos.QuizDTO;
import com.demo.models.entities.QuizEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 165139
 */
public class QuizMapper {
    private QuizMapper() {
    }

    public static QuizDTO toDTO(QuizEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new QuizDTO();
        dto.setQuizId(entity.getQuizId());
        dto.setQuizName(entity.getQuizName());
        dto.setDescription(dto.getDescription());
        dto.setQuestions(QuestionMapper.toDTO(entity.getQuestions()));
        return dto;
    }

    public static List<QuizDTO> toDTO(List<QuizEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return List.of();
        }

        return entities.stream().map(QuizMapper::toDTO).toList();
    }
}
