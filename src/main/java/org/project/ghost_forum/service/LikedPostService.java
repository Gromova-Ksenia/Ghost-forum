package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.LikedPostDto;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.entity.LikedPost;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.mapper.LikedPostMapper;
import org.project.ghost_forum.mapper.PostMapper;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.LikedPostRepository;
import org.project.ghost_forum.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikedPostService {
    private final LikedPostRepository repository;
    private final LikedPostMapper mapper;
    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public char getPostRate(UUID postId) {
        UserDto user = userService.getCurrent();
        UUID userId = user.getId();

        return repository.findByUserIdAndPostId(userId, postId)
                .map(LikedPost::getRate).orElse('0');

    }

    //Добавление оценки
    @Transactional
    public LikedPostDto newRate(LikedPostDto likedPostDto) {
        //Получаем пользователя, от которого запрос
        User user = userMapper.toEntity(userService.getCurrent());
        UUID postId = likedPostDto.getPostId();
        char rate = likedPostDto.getRate().charAt(0);

        //Если раньше оценки не было
        if (repository.findByUserIdAndPostId(user.getId(), postId).isEmpty()) {
            //Получаем пост
            Post post = postService.getPostById(postId);

            //Говорим что теперь оценка есть
            likedPostDto.setUserId(user.getId());
            LikedPost thisPost = mapper.toEntity(likedPostDto);
            LikedPost savedLikedPost = repository.save(thisPost);

            PostDto postDto = postMapper.toDto(post);
            if (rate == '+')
                postDto.increaseRating();
            else postDto.decreaseRating();

            postRepository.save(postMapper.toEntity(postDto));

            return mapper.toDto(savedLikedPost);
        } else {
            //Если оценка была, тащим пост
            Post post = postService.getPostById(postId);
            PostDto postDto = postMapper.toDto(post);

            return repository.findByUserIdAndPostId(user.getId(), postId).map(likedPost -> {
                //Обнуляем старую оценку
                if (likedPost.getRate() == '+')
                    postDto.decreaseRating();
                else postDto.increaseRating();
                //Бахаем новую
                if (rate == '+')
                    postDto.increaseRating();
                else postDto.decreaseRating();
                //Сохраняем
                postRepository.save(postMapper.toEntity(postDto));

                LikedPost newLikedPost = mapper.toEntity(likedPostDto);
                LikedPost savedLikedPost = repository.save(newLikedPost);
                return mapper.toDto(savedLikedPost);
            }).orElse(likedPostDto);//Мы сюда никогда не зайдём по определению, но так надо
        }

    }

    @Transactional
    public void deleteRate(LikedPostDto likedPostDto) {
        UUID userId = likedPostDto.getUserId();
        UUID postId = likedPostDto.getPostId();

        repository.findByUserIdAndPostId(userId, postId).map(likedPost -> {
            Post post = postService.getPostById(postId);
            PostDto postDto = postMapper.toDto(post);
            if (likedPost.getRate() == '+')
                postDto.decreaseRating();
            else postDto.increaseRating();

            postRepository.save(postMapper.toEntity(postDto));

            repository.delete(likedPost);
            return null;
        }).orElse(null);
    }
}
