package com.techcompany.sugcm.controllers;

import com.techcompany.sugcm.models.dto.AppointmentDto;
import com.techcompany.sugcm.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByPatientId(patientId));
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.saveAppointment(appointmentDto));
        } catch (Exception e) {
            return ResponseEntity.ok(AppointmentDto.builder().message(e.getMessage()).build());
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDto appointmentDto) {
        try {
            Optional<AppointmentDto> existingUser = appointmentService.getAppointmentById(appointmentId);
            if (existingUser.isPresent()) {
                appointmentDto.setAppointmentId(appointmentId);
                AppointmentDto updatedUser = appointmentService.saveAppointment(appointmentDto);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.ok(AppointmentDto.builder().message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
