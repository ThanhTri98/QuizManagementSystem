package com.demo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author 165139
 */
@Entity
@Table(name = "Role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "role_id")
    private int roleId;

    @JoinColumn(name = "role_name")
    private String roleName;
}
