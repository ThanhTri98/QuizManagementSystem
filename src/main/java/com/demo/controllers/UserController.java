package com.demo.controllers;

import com.demo.commons.exception.UserAlreadyExistsException;
import com.demo.models.dtos.UserDTO;
import com.demo.models.http.ResponseMessage;
import com.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 165139
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseMessage<UserDTO>> get(@PathVariable("userId") int userId) {
        ResponseMessage<UserDTO> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            responseMessage.setData(this.userService.findById(userId));
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<List<UserDTO>>> getAll() {
        ResponseMessage<List<UserDTO>> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            responseMessage.setData(this.userService.findAll());
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @PutMapping
    public ResponseEntity<ResponseMessage<Boolean>> add(@RequestBody UserDTO user) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            this.userService.add(user);
            responseMessage.ok(true, "Add user successful");
        } catch (UserAlreadyExistsException uex) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            responseMessage.error(false, uex.getMessage());
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(false, ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @PostMapping("/roles/{userId}/{roleId}")
    public ResponseEntity<ResponseMessage<Boolean>> updateRole(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            this.userService.updateRole(userId, roleId);
            responseMessage.ok(true, "Update user role successful");
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(false, ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessage<Boolean>> delete(@PathVariable("userId") int userId) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            this.userService.deleteById(userId);
            responseMessage.ok(true, String.format("Delete user %s successful", userId));
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage.error(ex.getMessage());
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

}
