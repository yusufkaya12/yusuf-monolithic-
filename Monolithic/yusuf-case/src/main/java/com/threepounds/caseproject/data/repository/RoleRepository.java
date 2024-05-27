package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(String name);
}
