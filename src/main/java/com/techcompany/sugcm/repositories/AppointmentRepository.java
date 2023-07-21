package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Appointment;
import com.techcompany.sugcm.models.entity.Doctor;
import com.techcompany.sugcm.models.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndStartDateTimeBetween(Doctor doctor, LocalDateTime startDateTime,
                                                          LocalDateTime endDateTime);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);
}
