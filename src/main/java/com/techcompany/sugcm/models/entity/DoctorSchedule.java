package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "doctor_schedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_schedule_id")
    private Long doctorScheduleId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek; // Día de la semana (LUNES, MARTES, ...)

    @Column(nullable = false)
    private LocalTime startTime; // Hora de inicio de atención

    @Column(nullable = false)
    private LocalTime endTime; // Hora de fin de atención

    @OneToMany(mappedBy = "doctorSchedule")
    private List<Appointment> appointments; // Lista de citas para este horario
}
