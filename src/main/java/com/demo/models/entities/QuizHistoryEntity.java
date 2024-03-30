package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author 165139
 */
@Entity
@Table(name = "quiz_history")
@Data
public class QuizHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qh_id")
    private int qhId;

    private double score;

    @Column(name = "completion_time")
    private Date completionTime;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

}
