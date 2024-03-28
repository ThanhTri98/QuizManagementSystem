package com.demo.models;

import jakarta.persistence.*;

/**
 * @author 165139
 */
@Entity
public class Option {
    @Id
    @GeneratedValue
    private long optionId;
    private String optionText;
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
