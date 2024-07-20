package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService service;

    @GetMapping("/ban")
    public Boolean banUser(@RequestParam UUID id) {
        return service.banUser(id);
    }

    @GetMapping("/unban")
    public Boolean unbanUser(@RequestParam UUID id) {
        return service.unbanUser(id);
    }
}

