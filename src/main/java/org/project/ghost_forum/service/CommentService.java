package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.entity.Comment;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.mapper.CommentMapper;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserService userService;
    private PostService postService;
    private final UserMapper userMapper;

    public Set<Comment> getComments(List<UUID> commentIds){
        return commentIds.stream()
                .map(repository::findById)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Transactional
    public CommentDto newComment(CommentDto commentDto){
        User user = userMapper.toEntity(userService.getCurrent());
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
    public CommentDto editComment(CommentDto commentDto){
        Comment comment = repository.findById(commentDto.getId()).orElseThrow();

        Comment savedComment = repository.save(mapper.merge(commentDto, comment));

        return mapper.toDto(savedComment);
    }

    public Comment getCommentById(UUID id){
        return repository.findById(id).orElseThrow();
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

    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
    }
}
