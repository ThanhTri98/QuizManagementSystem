package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author 165139
 */
@Entity
@Table(name = "user_jwt")
@Data
public class UserJwtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jwt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "modified_date")
    private Date modifiedDate;
}
