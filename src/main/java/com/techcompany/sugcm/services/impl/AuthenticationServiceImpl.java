package com.techcompany.sugcm.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcompany.sugcm.models.auth.AuthenticationRequest;
import com.techcompany.sugcm.models.auth.AuthenticationResponse;
import com.techcompany.sugcm.models.auth.ForgotPasswordResponse;
import com.techcompany.sugcm.models.auth.RegisterRequest;
import com.techcompany.sugcm.models.entity.Role;
import com.techcompany.sugcm.models.entity.Token;
import com.techcompany.sugcm.models.entity.User;
import com.techcompany.sugcm.models.entity.UserRole;
import com.techcompany.sugcm.repositories.RoleRepository;
import com.techcompany.sugcm.repositories.TokenRepository;
import com.techcompany.sugcm.repositories.UserRepository;
import com.techcompany.sugcm.repositories.UserRoleRepository;
import com.techcompany.sugcm.services.AuthenticationService;
import com.techcompany.sugcm.util.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("El " + existingUser.get().getProfile() + " ya se encuentra registrado.");
        } else {
            Optional<Role> role = roleRepository.findByName(request.getProfile());
            if (role.isPresent()) {
                var user = User.builder()
                        .name(request.getName())
                        .lastname(request.getLastname())
                        .mobilePhone(request.getMobilePhone())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .profile(request.getProfile())
                        .build();
                User savedUser = userRepository.save(user);
                UserRole userRole = new UserRole();
                userRole.setUser(savedUser);
                userRole.setRole(role.get());
                userRoleRepository.save(userRole);
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                saveUserToken(savedUser, jwtToken);

                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                throw new Exception("El rol del usuario es inválido.");
            }
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Usuario no existe."));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ForgotPasswordResponse requestNewPassword(String email) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            var user = existingUser.get();
            // Generate and store a password reset token
            var token = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, token);

            // Send the password reset email with the token
            sendPasswordResetEmail(user, token);

            return ForgotPasswordResponse.builder()
                    .message("Se autorizo el cambio de clave, revise su bandeja de correo.")
                    .status(Boolean.TRUE)
                    .build();
        }
        throw new Exception("El correo no esta registrado.");
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void sendPasswordResetEmail(User user, String token) {
        // Implement your logic to send the password reset email
        String subject = "[SGCM]RECUPERACIÓN DE CLAVE";
        String body = "Para resetear su contraseña ingrese al siguiente enlace: http://localhost:4200/recover-password?recover_password_token=" + token;
        emailService.sendEmail(user.getEmail(), subject, body);
    }
}
