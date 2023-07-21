package com.techcompany.sugcm.models.entity;

import com.techcompany.sugcm.util.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime; // Fecha y hora de inicio de la cita

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime; // Fecha y hora de fin de la cita

    @Column(length = 2000)
    private String notes; // Notas adicionales para la cita

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status; // Estado de la cita (pendiente, completada, cancelada, etc.)

    @ManyToOne
    @JoinColumn(name = "doctor_schedule_id", nullable = false)
    private DoctorSchedule doctorSchedule;
}
