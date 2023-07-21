package com.techcompany.sugcm.controllers;

import com.techcompany.sugcm.models.auth.*;
import com.techcompany.sugcm.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.ok(AuthenticationResponse.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest passwordRequest) {
        try {
            return ResponseEntity.ok(service.requestNewPassword(passwordRequest.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.ok(ForgotPasswordResponse.builder()
                    .message(e.getMessage())
                    .status(Boolean.FALSE)
                    .build());
        }
    }
}
