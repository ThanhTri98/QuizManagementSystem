package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 165139
 */
@Entity
@Table(name = "quiz_history_detail")
@Data
public class QuizHistoryDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qhd_id")
    private int qhdId;

    @ManyToOne
    @JoinColumn(name = "qh_id")
    @ToString.Exclude
    private QuizHistoryEntity quizHistory;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @ToString.Exclude
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    @ToString.Exclude
    private OptionEntity option;

    @Column(name = "created_date")
    private Date createdDate;
}
