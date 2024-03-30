package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author 165139
 */
@Entity
@Table(name = "quiz")
@Data
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "quiz_name")
    private String quizName;

    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;
}
