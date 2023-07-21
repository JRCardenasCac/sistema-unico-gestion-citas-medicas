package com.techcompany.sugcm.models.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyDto {
    private Long specialtyId;
    private String name;
    private String message;
}
