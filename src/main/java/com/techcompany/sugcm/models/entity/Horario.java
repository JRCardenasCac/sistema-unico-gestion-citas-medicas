package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@Builder
public class Horario {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "horario_id")
    private Long horarioId;
    private String dia;
    private String jornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;*/
}
