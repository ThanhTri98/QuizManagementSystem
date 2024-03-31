package com.demo.models.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author 165139
 */
@Data
public class QuizHistoryDTO {
    private int qhId;
    private double score;
    private Date completionTime;
    private Date createdDate;
    private int userId;
    private QuizDTO quiz;
}
