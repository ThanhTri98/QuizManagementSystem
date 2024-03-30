package com.demo.commons.mapper;

import com.demo.models.dtos.QuestionDTO;
import com.demo.models.entities.QuestionEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 165139
 */
public class QuestionMapper {
    private QuestionMapper() {
    }

    public static QuestionDTO toDTO(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new QuestionDTO();
        dto.setQuestionId(entity.getQuestionId());
        dto.setQuestionText(entity.getQuestionText());
        dto.setQuizId(entity.getQuiz().getQuizId());
        dto.setOptions(OptionMapper.toDTO(entity.getOptions()));
        return dto;
    }

    public static List<QuestionDTO> toDTO(List<QuestionEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return List.of();
        }

        return entities.stream().map(QuestionMapper::toDTO).toList();
    }

    public static QuestionEntity toEntity(QuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        var entity = new QuestionEntity();
        entity.setQuestionText(dto.getQuestionText());
        return entity;
    }

    public static List<QuestionEntity> toEntity(List<QuestionDTO> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return List.of();
        }

        return dtos.stream().map(QuestionMapper::toEntity).toList();
    }

}
