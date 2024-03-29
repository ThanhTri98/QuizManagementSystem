package com.demo.exceptions;

/**
 * @author 165139
 */
public class UserAlreadyExistsException extends Exception {
    private final String identify;

    public UserAlreadyExistsException(String identify) {
        this.identify = identify;
    }

    @Override
    public String getMessage() {
        return String.format("User email %s already exists!", this.identify);
    }
}
