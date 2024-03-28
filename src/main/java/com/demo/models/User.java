package com.demo.models;

import jakarta.persistence.*;

/**
 * @author 165139
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String userName;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
