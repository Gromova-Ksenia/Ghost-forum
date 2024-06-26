package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.mapper.PostMapper;
import org.project.ghost_forum.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    private final PostMapper mapper;

    @GetMapping
    public List<PostDto> getAllPosts(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable UUID id){
        return mapper.toDto(service.getPostById(id));
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto){
        return service.createPost(postDto);
    }

    @PutMapping("/edit")
    public PostDto editPost(@RequestBody PostDto postDto){
        return service.editPost(postDto);
    }

}
