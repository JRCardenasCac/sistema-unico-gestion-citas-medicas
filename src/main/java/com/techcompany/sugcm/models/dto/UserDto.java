package com.techcompany.sugcm.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private String lastname;
    private String mobilePhone;
    private String email;
    private String password;
    private String profile;
    private String userCreation;
    private LocalDateTime creationDate;
    private String userModification;
    private LocalDateTime modificationDate;
    private String message;
}
