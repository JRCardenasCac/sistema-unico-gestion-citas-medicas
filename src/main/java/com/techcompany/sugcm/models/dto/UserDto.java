package com.techcompany.sugcm.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
}
