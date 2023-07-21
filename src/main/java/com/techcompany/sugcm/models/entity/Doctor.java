package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private List<Specialty> specialties; // Lista de especialidades del médico

    @OneToMany(mappedBy = "doctor")
    private List<DoctorSchedule> doctorSchedules; // Lista de horarios del médico

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments; // Lista de citas del médico
}
