package org.project.ghost_forum.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.dto.TagDto;
import org.project.ghost_forum.entity.Comment;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.Tag;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.mapper.CommentMapper;
import org.project.ghost_forum.mapper.PostMapper;
import org.project.ghost_forum.mapper.TagMapper;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.PostRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private final TagMapper tagMapper;


    public List<PostDto> getAll(){
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional
    public PostDto createPost(PostDto postDto){
        User author = userMapper.toEntity(userService.getCurrent());

        List<UUID> tagIds = postDto.getTags().stream().map(TagDto::getId).collect(Collectors.toList());
        Set<Tag> postTags = tagService.getTags(tagIds);

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

    public Post getPostById(UUID id){
        return repository.findById(id).orElseThrow();
    }

    public PostDto findById(UUID id){
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Transactional
    public void deletePost(UUID id){
        repository.deleteById(id);
    }

    public List<PostDto> getPostsByUser(UUID userId){
        return repository.findAllByAuthor(userId).stream()
                .map(mapper::toDto).toList();
    }

    public List<PostDto> getPostsByUser(String username){
        User user = userService.getUserByUsername(username);
        return repository.findAllByAuthor(user.getId()).stream()
                .map(mapper::toDto).toList();
    }

    @Transactional
    public PostDto editPost(PostDto postDto){
        User author = userMapper.toEntity(userService.getCurrent());

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
    }
}
