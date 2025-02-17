package com.babbangona.commons.library.repo;

import com.babbangona.commons.library.entities.Tenant;
import com.babbangona.commons.library.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"userRoles", "userRoles.privileges"})
    Optional<User> findByUsername(String username);
    Optional<User> findByTenant_Id(Long tenantId);

}
