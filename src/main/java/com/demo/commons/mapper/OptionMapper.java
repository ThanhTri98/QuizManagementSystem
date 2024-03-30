package com.demo.commons.mapper;

import com.demo.models.dtos.OptionDTO;
import com.demo.models.entities.OptionEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 165139
 */
public class OptionMapper {
    private OptionMapper() {
    }

    public static OptionDTO toDTO(OptionEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new OptionDTO();
        dto.setOptionId(entity.getOptionId());
        dto.setOptionText(entity.getOptionText());
        dto.setCorrect(entity.isCorrect());
        dto.setQuestionId(entity.getQuestion().getQuestionId());
        return dto;
    }

    public static List<OptionDTO> toDTO(List<OptionEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return List.of();
        }

        return entities.stream().map(OptionMapper::toDTO).toList();
    }
}
