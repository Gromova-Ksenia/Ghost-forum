package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.entity.Comment;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.exception.CreatingException;
import org.project.ghost_forum.mapper.CommentMapper;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.CommentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserService userService;
    private final UserMapper userMapper;


    @Transactional
    public CommentDto newComment(CommentDto commentDto) {
        if (commentDto.getBody().isEmpty()) throw new CreatingException("Комментарий не может быть пустым.");

        commentDto.setCreationTime(LocalDateTime.now());
        commentDto.setUserId(userService.getCurrent().getId());

        Comment comment = mapper.toEntity(commentDto);

        Comment savedComment = repository.save(comment);
        return mapper.toDto(savedComment);
    }

    @Transactional
    public CommentDto editComment(CommentDto commentDto) {
        if (commentDto.getBody().isEmpty()) throw new CreatingException("Комментарий не может быть пустым.");

        UserDto user = userService.getCurrent();

        if (user.getId().equals(commentDto.getUserId())) {
            Comment comment = repository.findById(commentDto.getId()).orElseThrow();

            Comment savedComment = repository.save(mapper.merge(commentDto, comment));

            return mapper.toDto(savedComment);
        } else throw new AccessDeniedException("Ошибка! Нет прав на редактирование.");

    }

    @Transactional
    public void deleteComment(UUID id) {
        User user = userMapper.toEntity(userService.getCurrent());
        Comment comment = repository.findById(id).orElse(null);

        if (user.getId().equals(comment.getUser().getId()) || (userService.hasAdminAuthority(user.getId()))) {
            repository.delete(comment);
        } else throw new AccessDeniedException("Ошибка! У пользователя нет прав на удаление.");
    }

    //Иначе ошибка
    private CommentDto transformToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .userUsername(comment.getUser().getUsername())
                .creationTime(comment.getCreationTime())
                .body(comment.getBody())
                .build();
    }

    //Получаем комменты к посту
    public Set<CommentDto> getCommentsToPost(UUID postId) {
        return repository.findAllByPost(postId).stream()
                .map(this::transformToDto).collect(Collectors.toSet());
    }
}
