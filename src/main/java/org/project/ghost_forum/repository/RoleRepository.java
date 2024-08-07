package org.project.ghost_forum.repository;

import org.project.ghost_forum.entity.Role;
import org.project.ghost_forum.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("FROM Role u WHERE u.roleType = :name")
    Role findByName(RoleType name);
}
