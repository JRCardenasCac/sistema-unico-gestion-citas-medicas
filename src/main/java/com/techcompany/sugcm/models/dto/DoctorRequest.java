package com.techcompany.sugcm.models.dto;

import com.techcompany.sugcm.models.entity.Especialidad;
import com.techcompany.sugcm.models.entity.Horario;

import java.util.List;

public class DoctorRequest {
    private String name;
    private String lastname;
    private String mobilePhone;
    private String email;
    private String password;
    private String profile;
    private Especialidad especialidad;
    private List<Horario> horarios;
}
