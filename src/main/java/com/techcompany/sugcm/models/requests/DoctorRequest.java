package com.techcompany.sugcm.models.requests;

import com.techcompany.sugcm.models.entity.Specialty;
import com.techcompany.sugcm.models.entity.DoctorSchedule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorRequest {
    private String name;
    private String lastname;
    private String mobilePhone;
    private String email;
    private String password;
    private String profile;
    private List<DoctorSchedule> doctorSchedules;
    private List<Specialty> specialties;
}
