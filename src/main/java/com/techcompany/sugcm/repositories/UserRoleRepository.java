package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
