package com.techcompany.sugcm.services;

import com.techcompany.sugcm.models.dto.UserDto;
import com.techcompany.sugcm.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    UserDto saveUser(User user) throws Exception;

    void deleteUser(Long id);
}
