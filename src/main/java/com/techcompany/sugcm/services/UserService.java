package com.techcompany.sugcm.services;

import com.techcompany.sugcm.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User saveUser(User user) throws Exception;

    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);
}
