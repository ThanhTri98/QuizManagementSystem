package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author 165139
 */
@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;
}
