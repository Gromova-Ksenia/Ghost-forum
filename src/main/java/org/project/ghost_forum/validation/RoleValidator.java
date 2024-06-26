package org.project.ghost_forum.validation;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.RoleDto;
import org.project.ghost_forum.service.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RoleValidator implements Validator {
    private final RoleService roleService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoleDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        RoleDto targetRole = (RoleDto) target;

        boolean exists = roleService.findAll().stream()
                .anyMatch(role -> targetRole.getRole().equals(role.getRoleType().name()));

        if (exists){
            errors.rejectValue("role", "");
        }
    }
}
