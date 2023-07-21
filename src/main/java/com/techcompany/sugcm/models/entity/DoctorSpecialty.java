package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctor_specialty")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorSpecialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_specialty_id")
    private Long doctorSpecialtyId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;
}
