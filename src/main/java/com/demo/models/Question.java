package com.demo.models;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author 165139
 */
@Entity
public class Question {
    @Id
    @GeneratedValue
    private int questionId;
    private String questionText;
    @ManyToOne
    @JoinColumn(name = "quizId")
    private Quiz quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
