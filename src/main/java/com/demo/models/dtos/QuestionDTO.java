package com.demo.models.dtos;

import lombok.Data;

import java.util.List;

/**
 * @author 165139
 */
@Data
public class QuestionDTO {
    private int questionId;
    private String questionText;
    private int quizId;
    private List<OptionDTO> options;
}
