package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@Builder
public class Medico {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medico_id")
    private Long medicoId;

    @JoinColumn(name = "fk_especialidad")
    private Especialidad especialidad;

    @JoinColumn(name = "fk_user")
    private User user;*/
}
