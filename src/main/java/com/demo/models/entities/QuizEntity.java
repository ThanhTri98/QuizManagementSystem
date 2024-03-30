package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author 165139
 */
@Entity
@Table(name = "Quiz")
@Data
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "quiz_id")
    private int quizId;

    @JoinColumn(name = "quiz_name")
    private String quizName;

    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;
}
