package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.service.PostService;
import org.project.ghost_forum.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final PostService postService;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto){
        return service.addUser(userDto);
    }

    @GetMapping
    public UserDto getCurrentUser(){
        return  service.getCurrent();
    }

    @GetMapping("/{id}")
    public List<PostDto> getPostsByUser(@PathVariable UUID id){
        return postService.getPostsByUser(id);
    }

}
