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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments; // Lista de citas del paciente
}
