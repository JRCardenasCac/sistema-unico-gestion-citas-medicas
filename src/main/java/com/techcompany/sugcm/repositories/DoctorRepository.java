package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository  extends JpaRepository<Doctor, Long> {
}
