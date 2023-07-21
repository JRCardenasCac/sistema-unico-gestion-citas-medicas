package com.techcompany.sugcm.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techcompany.sugcm.util.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Long appointmentId;
    private DoctorDto doctor;
    private PatientDto patient;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;
    private String notes;
    private AppointmentStatus status;
    private DoctorScheduleDto doctorSchedule;
    private String message;
}
