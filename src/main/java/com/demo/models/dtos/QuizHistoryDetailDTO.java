package com.demo.models.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author 165139
 */
@Data
public class QuizHistoryDetailDTO {
    private int qhdId;
    private QuizHistoryDTO quizHistory;
    private QuestionDTO question;
    private OptionDTO option;
    private Date createdDate;
}
