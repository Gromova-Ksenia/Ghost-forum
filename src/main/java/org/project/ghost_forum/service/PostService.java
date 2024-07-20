package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.dto.TagDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.entity.Comment;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.Tag;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.exception.CreatingException;
import org.project.ghost_forum.mapper.CommentMapper;
import org.project.ghost_forum.mapper.PostMapper;
import org.project.ghost_forum.mapper.TagMapper;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.PostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TagService tagService;
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final TagMapper tagMapper;

    //Получить все посты
    public List<PostDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    //Новый пост
    @Transactional
    public PostDto createPost(PostDto postDto) {
        User author = userMapper.toEntity(userService.getCurrent());
        Set<Tag> postTags = new HashSet<>();

        if (postDto.getTags() != null) {
            List<UUID> tagIds = postDto.getTags().stream().map(TagDto::getId).collect(Collectors.toList());
            postTags = tagService.getTags(tagIds);
        }
        ;

        Post post = mapper.toEntity(postDto).builder()
                .author(author)
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .creationTime(LocalDateTime.now())
                .rating(0)
                .tags(postTags).build();

        Post savedPost = repository.save(post);
        return mapper.toDto(savedPost);
    }

    public Post getPostById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public PostDto findById(UUID id) {
        PostDto post = mapper.toDto(repository.findById(id).orElseThrow());
        Set<CommentDto> comments = commentService.getCommentsToPost(id);
        post.setComments(comments);
        return post;
    }

    //Удаление
    @Transactional
    public void deletePost(UUID id) {
        User user = userMapper.toEntity(userService.getCurrent());
        Post post = repository.findById(id).orElse(null);

        if (user.getId().equals(post.getAuthor().getId()) || (userService.hasAdminAuthority(user.getId()))) {
            repository.delete(post);
        } else throw new AccessDeniedException("Ошибка! У пользователя нет прав на удаление.");

    }

    public List<PostDto> getPostsByUser(UUID userId) {
        return repository.findAllByAuthor(userId).stream()
                .map(mapper::toDto).toList();
    }

    public List<PostDto> getPostsByUser(String username) {
        User user = userService.getUserByUsername(username);
        return repository.findAllByAuthor(user.getId()).stream()
                .map(mapper::toDto).toList();
    }

    //Редактирование
    @Transactional
    public PostDto editPost(PostDto postDto) {
        if (postDto.getBody().isEmpty() || postDto.getTitle().isEmpty())
            throw new CreatingException("Заголовок и тело поста не могут быть пустыми.");

        UserDto user = userService.getCurrent();

        if (user.getId().equals(postDto.getAuthorId())) {
            Set<Comment> comments = postDto.getComments().stream().map(commentMapper::toEntity).collect(Collectors.toSet());
            Set<Tag> tags = postDto.getTags().stream().map(tagMapper::toEntity).collect(Collectors.toSet());

            Post post = Post.builder()
                    .id(postDto.getId())
                    .title(postDto.getTitle())
                    .body(postDto.getBody())
                    .creationTime(postDto.getCreationTime())
                    .rating(postDto.getRating())
                    .comments(comments)
                    .tags(tags)
                    .build();

            Post savedPost = repository.save(post);

            return mapper.toDto(savedPost);
        } else throw new AccessDeniedException("Ошибка! Нет прав на редактирование.");
    }
}
