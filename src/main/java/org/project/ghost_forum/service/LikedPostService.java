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

    public char getPostRateForCurrentUser(UUID postId){
        UserDto user = userService.getCurrent();
        UUID userId = user.getId();

        return repository.findByUserIdAndPostId(userId, postId)
                .map(LikedPost::getRate).orElse('0');

    }

    //Едрён батон...
    @Transactional
    public LikedPostDto newRate(LikedPostDto likedPostDto){
        UUID userId = likedPostDto.getUserId();
        UUID postId = likedPostDto.getPostId();

        //Если раньше оценки не было
        if (repository.findByUserIdAndPostId(userId,postId).isEmpty()){
            //Получаем пост и юзера
            Post post = postService.getPostById(postId);
            User user = userService.getUserById(userId);

            //Говорим что теперь оценка есть
            LikedPost thisPost = mapper.toEntity(likedPostDto);
            LikedPost savedLikedPost = repository.save(thisPost);
            return mapper.toDto(savedLikedPost);
        } else {
            //Если оценка была, тащим пост
            Post post = postService.getPostById(postId);
            PostDto postDto = postMapper.toDto(post);

            return repository.findByUserIdAndPostId(userId, postId).map(likedPost -> {
                //Обнуляем старую оценку
                if(likedPost.getRate()=='+')
                postDto.decreaseRating();
                else postDto.increaseRating();
                //Бахаем новую
                if (likedPostDto.getRate()=='+')
                    postDto.increaseRating();
                else postDto.decreaseRating();
                //Сохраняем
                postRepository.save(postMapper.toEntity(postDto));

                LikedPost newLikedPost = mapper.toEntity(likedPostDto);
                LikedPost savedLikedPost = repository.save(newLikedPost);
                return mapper.toDto(savedLikedPost);
            }).orElse(likedPostDto);//Мы сюда никогда не зайдём по определению, но опшиналы тупые, поэтому да
        }
    }

    @Transactional
    public void deleteRate(LikedPostDto likedPostDto){
        UUID userId = likedPostDto.getUserId();
        UUID postId = likedPostDto.getPostId();

        repository.findByUserIdAndPostId(userId, postId).map(likedPost -> {
            Post post = postService.getPostById(postId);
            PostDto postDto = postMapper.toDto(post);
            if (likedPost.getRate()=='+')
                postDto.decreaseRating();
            else postDto.increaseRating();

            postRepository.save(postMapper.toEntity(postDto));

            repository.delete(likedPost);
            return null;
        }).orElseThrow();
    }

}
