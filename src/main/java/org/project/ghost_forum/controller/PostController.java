package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable("id") UUID uuid) {
        return service.findById(uuid);
    }

    @PostMapping("/new-post")
    public PostDto createPost(@RequestBody PostDto postDto) {
        return service.createPost(postDto);
    }

    @PutMapping("/edit")
    public PostDto editPost(@RequestBody PostDto postDto) {
        return service.editPost(postDto);
    }

    @DeleteMapping("/delete-post")
    public void deletePost(@RequestParam UUID id) {
        service.deletePost(id);
    }

}
