package org.project.ghost_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private LocalDate registrationDate;
    private String description;
    private Set<RoleDto> roles;
}
