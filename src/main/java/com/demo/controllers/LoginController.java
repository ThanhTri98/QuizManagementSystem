package com.demo.controllers;

import com.demo.models.http.ResponseMessage;
import com.demo.services.security.JwtTokenProvider;
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
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
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

            final String jwt = this.tokenProvider.generateToken(((UserDetailsImpl) authenticationResponse.getPrincipal()).getEmail());
            responseMessage.setData(jwt);
        } catch (Exception ex) {
            status = HttpStatus.UNAUTHORIZED;
            responseMessage.setError(true);
            responseMessage.setMessage("Email or Password invalid!");
        }
        return ResponseEntity.status(status).body(responseMessage);
    }


}
