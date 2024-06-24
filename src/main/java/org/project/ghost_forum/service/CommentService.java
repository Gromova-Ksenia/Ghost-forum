package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.entity.Comment;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.mapper.CommentMapper;
import org.project.ghost_forum.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    CommentRepository repository;
    CommentMapper mapper;
    UserService userService;
    PostService postService;

    public Set<Comment> getComments(List<UUID> commentIds){
        return commentIds.stream()
                .map(repository::findById)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Transactional
    public CommentDto newComment(CommentDto commentDto){
        User user = userService.getUserById(commentDto.getUserId());
        Post post = postService.getPostById(commentDto.getPostId());

        Comment comment = mapper.toEntity(commentDto).builder()
                .post(post)
                .user(user)
                .creationTime(LocalDateTime.now())
                .body(commentDto.getBody())
                .build();

        Comment savedComment = repository.save(comment);
        return mapper.toDto(savedComment);
    }

    @Transactional
    public void deleteComment(UUID id){
        repository.deleteById(id);
    }

    //Иначе ошибка неизвестного рода
    private CommentDto transformToDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .userUsername(comment.getUser().getUsername())
                .creationTime(comment.getCreationTime())
                .body(comment.getBody())
                .build();
    }

    //Комменты к посту
    public List<CommentDto> getCommentsToPost(UUID postId){
        return repository.findAllByPost(postId).stream()
                .map(this::transformToDto).toList();
    }
}
