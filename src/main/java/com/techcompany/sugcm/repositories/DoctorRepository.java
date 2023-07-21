package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(value = "SELECT d.* FROM doctor d" +
            " INNER JOIN doctor_specialty ds ON d.doctor_id = ds.doctor_id" +
            " WHERE ds.specialty_id = :specialtyId", nativeQuery = true)
    List<Doctor> findBySpecialtyId(@Param("specialtyId") Long specialtyId);
}
