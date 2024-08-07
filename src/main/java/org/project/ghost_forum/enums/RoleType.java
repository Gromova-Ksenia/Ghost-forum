package org.project.ghost_forum.enums;

import java.util.Arrays;
import java.util.Objects;

public enum RoleType {

    ROLE_USER,

    ROLE_ADMIN;

    public static RoleType fromString(String role) {
        return Arrays.stream(RoleType.values())
                .filter(type -> Objects.equals(role, type.name()))
                .findAny().orElse(null);
    }
}
