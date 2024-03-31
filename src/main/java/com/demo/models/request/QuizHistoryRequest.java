package com.demo.models.request;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 165139
 */
@Data
public class QuizHistoryRequest {
    private int quizId;
    private int userId;
    private Date createdDate;
    private Date completionDate;
    /**
     * Key: questionId
     * Value: Option Id list selected, example [1,4]
     */
    private Map<Integer, List<Long>> userAnswer;
}
