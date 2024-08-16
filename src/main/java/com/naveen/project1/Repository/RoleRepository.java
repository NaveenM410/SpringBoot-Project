package com.naveen.project1.Repository;
import com.naveen.project1.Model.AppRole;
import com.naveen.project1.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
