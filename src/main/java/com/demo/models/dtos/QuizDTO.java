package com.demo.models.dtos;

import lombok.Data;

import java.util.List;

/**
 * @author 165139
 */
@Data
public class QuizDTO {
    private int quizId;
    private String quizName;
    private String description;
    private List<QuestionDTO> questions;
}
