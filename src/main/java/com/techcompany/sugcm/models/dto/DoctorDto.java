package com.techcompany.sugcm.models.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long doctorId;
    private UserDto user;
}
