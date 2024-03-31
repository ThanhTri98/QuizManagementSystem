package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 165139
 */
@Entity
@Table(name = "question")
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @ToString.Exclude
    private QuizEntity quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OptionEntity> options;
}
