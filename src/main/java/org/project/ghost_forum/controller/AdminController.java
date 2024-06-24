package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService service;

    @GetMapping("/current")
    public UserDto getCurrent() {
        return service.getCurrent();
    }

    @GetMapping("/ban{id}")
    public Boolean banUser(@PathVariable UUID id) {
        return service.banUser(id);
    }

    @GetMapping("/unban{id}")
    public Boolean unbanUser(@PathVariable UUID id) {
        return service.unbanUser(id);
    }
}
