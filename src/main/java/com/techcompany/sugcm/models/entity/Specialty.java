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
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "specialties")
    private List<Doctor> doctors; // Lista de médicos con esta especialidad

    @OneToMany(mappedBy = "specialty")
    private List<Appointment> appointments; // Lista de citas asociadas a esta especialidad
}
