package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.UserRepository;
import org.project.ghost_forum.service.PostService;
import org.project.ghost_forum.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private final UserRepository repository;
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
    public UserDto getUserById(@PathVariable UUID id){
        return mapper.toDto(service.getUserById(id));
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getPostsByUser(@PathVariable UUID id){
        return postService.getPostsByUser(id);
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable String username){
        return mapper.toDto(service.getUserByUsername(username));
    }

    @GetMapping("/{username}/posts")
    public List<PostDto> getPostsByUser(@PathVariable String username){
        return postService.getPostsByUser(username);
    }
}
