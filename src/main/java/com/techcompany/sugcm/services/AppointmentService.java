package com.techcompany.sugcm.services;

import com.techcompany.sugcm.models.dto.AppointmentDto;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<AppointmentDto> getAllAppointments();

    List<AppointmentDto> getAllAppointmentsByPatientId(Long patientId);

    List<AppointmentDto> getAllAppointmentsByDoctorId(Long doctorId);

    Optional<AppointmentDto> getAppointmentById(Long id);

    AppointmentDto saveAppointment(AppointmentDto appointmentDto) throws Exception;

    void deleteAppointment(Long id);
}
