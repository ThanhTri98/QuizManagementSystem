package com.demo.controllers;

import com.demo.models.User;
import com.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 165139
 */
@RestController("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId") int userId) {
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            user = userService.findById(userId).orElse(null);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = List.of();
        HttpStatus status = HttpStatus.OK;
        try {
            users = userService.findAll();
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(users);
    }

    @PutMapping
    public ResponseEntity<Boolean> add(@RequestBody User user) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            userService.add(user);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @PostMapping
    public ResponseEntity<Boolean> update(@RequestBody User user) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            userService.update(user);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable("userId") int userId) {
        boolean result = true;
        HttpStatus status = HttpStatus.OK;
        try {
            userService.deleteById(userId);
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result = false;
        }
        return ResponseEntity.status(status).body(result);
    }

}
