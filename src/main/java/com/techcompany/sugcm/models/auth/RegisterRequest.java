package com.techcompany.sugcm.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techcompany.sugcm.models.entity.User;
import jakarta.persistence.Column;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends User {
    private String name;
    private String lastname;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    private String email;
    private String password;
    private String profile;
}
