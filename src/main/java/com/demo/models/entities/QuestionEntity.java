package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author 165139
 */
@Entity
@Table(name = "Question")
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "question_id")
    private int questionId;

    @JoinColumn(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<OptionEntity> options;
}
