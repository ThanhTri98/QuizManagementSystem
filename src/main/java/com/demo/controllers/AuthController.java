package com.demo.controllers;

import com.demo.models.http.ResponseMessage;
import com.demo.services.UserJwtService;
import com.demo.services.security.LoginRequest;
import com.demo.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 165139
 */
@RestController
@RequestMapping("${config.api.prefix}")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserJwtService userJwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserJwtService userJwtService) {
        this.authenticationManager = authenticationManager;
        this.userJwtService = userJwtService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseMessage<String>> login(@RequestBody LoginRequest loginRequest) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>();
        HttpStatus status = HttpStatus.OK;
        try {
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);

            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

            UserDetailsImpl userDetails = (UserDetailsImpl) authenticationResponse.getPrincipal();

            final String jwt = this.userJwtService.getJwtTokenProvider().generateToken(userDetails.getUserId(), userDetails.getEmail());

            // Luu Database
            this.userJwtService.upsert(userDetails.getUserId(), jwt);
            responseMessage.setData(jwt);
        } catch (Exception ex) {
            status = HttpStatus.UNAUTHORIZED;
            responseMessage.error("Email or Password invalid!");
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

}
