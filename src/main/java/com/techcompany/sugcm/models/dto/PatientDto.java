package com.techcompany.sugcm.models.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long patientId;
    private UserDto user;
}
