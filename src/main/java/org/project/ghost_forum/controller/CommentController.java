package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {
    private final CommentService service;

    @PostMapping("/new-comment")
    public CommentDto createComment(@RequestBody CommentDto commentDto){
        return service.newComment(commentDto);
    }

    @DeleteMapping("/delete-comment")
    public void deleteComment(@RequestParam UUID id){
        service.deleteComment(id);
    }

    @GetMapping("/post{id}")
    public List<CommentDto> getCommentsToPost(@PathVariable UUID id){
        return service.getCommentsToPost(id);
    }
}
