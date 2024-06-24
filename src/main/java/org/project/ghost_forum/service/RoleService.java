package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.RoleDto;
import org.project.ghost_forum.entity.Role;
import org.project.ghost_forum.enums.RoleType;
import org.project.ghost_forum.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public void addRole(RoleDto role){
        repository.save(Role.builder().roleType(RoleType.fromString(role.getRole())).build());
    }

    public List<Role> findAll(){
        return repository.findAll();
    }

    public Set<Role> getRoles(List<UUID> roleIds){
        return roleIds.stream()
                .map(repository::findById)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
