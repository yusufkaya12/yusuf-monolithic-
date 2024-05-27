package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.Permission;
import com.threepounds.caseproject.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String name);
}
