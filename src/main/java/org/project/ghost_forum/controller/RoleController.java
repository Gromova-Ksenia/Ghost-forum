package org.project.ghost_forum.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.RoleDto;
import org.project.ghost_forum.service.RoleService;
import org.project.ghost_forum.validation.RoleValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleValidator validator;

    @PostMapping //Добавление роли
    public void addRole(@RequestBody RoleDto roleDto, BindingResult bindingResult) {
        validator.validate(roleDto, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Уже присутствует роль " + roleDto);
        }

        roleService.addRole(roleDto);
    }
}
