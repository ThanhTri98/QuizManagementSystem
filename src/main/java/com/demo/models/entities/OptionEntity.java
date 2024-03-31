package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

/**
 * @author 165139
 */
@Entity
@Table(name = "option")
@Data
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private long optionId;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @ToString.Exclude
    private QuestionEntity question;
}
