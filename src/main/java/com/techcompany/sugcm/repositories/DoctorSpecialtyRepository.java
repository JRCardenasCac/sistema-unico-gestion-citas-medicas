package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.DoctorSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, Long> {
}
