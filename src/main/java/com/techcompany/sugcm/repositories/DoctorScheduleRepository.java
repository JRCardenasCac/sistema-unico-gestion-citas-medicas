package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Doctor;
import com.techcompany.sugcm.models.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorAndDayOfWeek(Doctor doctor, DayOfWeek dayOfWeek);

    List<DoctorSchedule> findByDoctor(Doctor doctor);
}