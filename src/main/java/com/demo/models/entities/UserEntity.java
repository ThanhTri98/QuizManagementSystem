package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author 165139
 */
@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
