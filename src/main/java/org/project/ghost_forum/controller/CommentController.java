package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {
    private final CommentService service;

    @PostMapping("/new-comment")
    public void createComment(@RequestBody CommentDto commentDto){
        service.newComment(commentDto);
    }

    @GetMapping("/post{id}")
    public List<CommentDto> getCommentsToPost(@PathVariable UUID id){
        return service.getCommentsToPost(id);
    }
}
