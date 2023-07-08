package com.techcompany.sugcm.services;

import com.techcompany.sugcm.models.auth.AuthenticationRequest;
import com.techcompany.sugcm.models.auth.AuthenticationResponse;
import com.techcompany.sugcm.models.auth.ForgotPasswordResponse;
import com.techcompany.sugcm.models.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws Exception;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    ForgotPasswordResponse requestNewPassword(String email) throws Exception;
}
