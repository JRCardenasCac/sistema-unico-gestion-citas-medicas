package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Patient;
import com.techcompany.sugcm.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}
