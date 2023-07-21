package com.techcompany.sugcm.models.dto;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleDto {
    private Long doctorScheduleId;
    private DoctorDto doctor;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
