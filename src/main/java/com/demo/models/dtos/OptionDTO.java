package com.demo.models.dtos;

import lombok.Data;

/**
 * @author 165139
 */
@Data
public class OptionDTO {
    private long optionId;
    private String optionText;
    private boolean isCorrect;
    private int questionId;
}
