package com.techcompany.sugcm.controllers;

import com.techcompany.sugcm.models.dto.UserDto;
import com.techcompany.sugcm.models.entity.User;
import com.techcompany.sugcm.models.requests.DoctorRequest;
import com.techcompany.sugcm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hola mundo");
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/administrators")
    public ResponseEntity<UserDto> createAdministrator(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveAdministrator(user));
        } catch (Exception e) {
            return ResponseEntity.ok(UserDto.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/doctors")
    public ResponseEntity<UserDto> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveDoctor(doctorRequest));
        } catch (Exception e) {
            return ResponseEntity.ok(UserDto.builder().message(e.getMessage()).build());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            Optional<UserDto> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                user.setUserId(id);
                UserDto updatedUser = userService.saveAdministrator(user);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.ok(UserDto.builder().message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
