package com.techcompany.sugcm.services;

import com.techcompany.sugcm.models.requests.DoctorRequest;
import com.techcompany.sugcm.models.dto.UserDto;
import com.techcompany.sugcm.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    UserDto saveAdministrator(User user) throws Exception;

    UserDto saveDoctor(DoctorRequest doctorRequest) throws Exception;

    void deleteUser(Long id);
}
