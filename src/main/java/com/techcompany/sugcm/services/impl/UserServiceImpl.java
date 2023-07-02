package com.techcompany.sugcm.services.impl;

import com.techcompany.sugcm.models.entity.Role;
import com.techcompany.sugcm.models.entity.Token;
import com.techcompany.sugcm.models.entity.User;
import com.techcompany.sugcm.models.entity.UserRole;
import com.techcompany.sugcm.repositories.RoleRepository;
import com.techcompany.sugcm.repositories.TokenRepository;
import com.techcompany.sugcm.repositories.UserRepository;
import com.techcompany.sugcm.repositories.UserRoleRepository;
import com.techcompany.sugcm.services.UserService;
import com.techcompany.sugcm.util.enums.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) throws Exception {
        Optional<User> existingUser = getUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("El " + existingUser.get().getProfile() + " ya se encuentra registrado.");
        } else {
            Optional<Role> role = roleRepository.findByName(user.getProfile());
            if (role.isPresent()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userRepository.save(user);
                UserRole userRole = new UserRole();
                userRole.setUser(savedUser);
                userRole.setRole(role.get());
                userRoleRepository.save(userRole);
                var jwtToken = jwtService.generateToken(user);
                jwtService.generateRefreshToken(user);
                saveUserToken(savedUser, jwtToken);

                return savedUser;
            } else {
                throw new Exception("El rol del usuario es inválido.");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
}
