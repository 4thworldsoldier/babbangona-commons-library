package com.babbangona.commons.library.repo;

import com.babbangona.commons.library.entities.Tenant;
import com.babbangona.commons.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByName(String name);
}
