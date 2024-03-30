package com.demo.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author 165139
 */
@Data
public class UserDTO {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private int roleId;

    public String getPassword() {
        return null;
    }

    @JsonIgnore
    public String getPrivatePassword() {
        return this.password;
    }
}
