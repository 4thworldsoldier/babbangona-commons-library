package com.babbangona.commons.library.repo;

import com.babbangona.commons.library.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //Optional<Role> findByRoleId(List<Long> roles);

    Optional<Role> findByName(String name);

    Set<Role> findByIdIn(List<Long> roleIds);


}
