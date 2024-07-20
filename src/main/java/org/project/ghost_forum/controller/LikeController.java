package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.LikedPostDto;
import org.project.ghost_forum.service.LikedPostService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikeController {
    private final LikedPostService service;

    @PostMapping("/set-rate")
    public LikedPostDto setRate(@RequestBody LikedPostDto likedPostDto) {
        return service.newRate(likedPostDto);
    }

    @DeleteMapping("/delete-rate")
    public void deleteRate(@RequestBody LikedPostDto likedPostDto) {
        service.deleteRate(likedPostDto);
    }

    @GetMapping("/{postId}/get-rate")
    public char getRate(@PathVariable UUID postId) {
        return service.getPostRate(postId);
    }
}
