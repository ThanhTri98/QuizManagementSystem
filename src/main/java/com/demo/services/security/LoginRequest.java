package com.demo.services.security;

import lombok.Data;

/**
 * @author 165139
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
