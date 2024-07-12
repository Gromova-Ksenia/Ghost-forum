package org.project.ghost_forum.controller;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.LikedPostDto;
import org.project.ghost_forum.dto.PostDto;
import org.project.ghost_forum.mapper.PostMapper;
import org.project.ghost_forum.service.LikedPostService;
import org.project.ghost_forum.service.PostService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpClient;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private final LikedPostService likedPostService;

    @GetMapping
    @ModelAttribute("allPosts")
    public void getAllPosts(Model model){
        model.addAttribute("allPosts", service.getAll());
    }

    @GetMapping("/{id}")
    public ModelAndView getPost(@ModelAttribute("post") PostDto postDto, @PathVariable("id") UUID uuid, Model model){
        model.addAttribute("post", service.findById(uuid));
        return new ModelAndView("post", "post", service.findById(uuid));
        //return "post";
    }

    @PostMapping("/new-post")
    public PostDto createPost(@RequestBody PostDto postDto){
        return service.createPost(postDto);
    }

    @PutMapping("/edit")
    public PostDto editPost(@RequestBody PostDto postDto){
        return service.editPost(postDto);
    }

    @PostMapping("/set-rate")
    public LikedPostDto setRate(@RequestBody LikedPostDto likedPostDto){
        return likedPostService.newRate(likedPostDto);
    }

    @DeleteMapping("/delete-rate")
    public void deleteRate(@RequestBody LikedPostDto likedPostDto){
        likedPostService.deleteRate(likedPostDto);
    }

    @GetMapping("/{postId}/get-rate")
    public char getRate(@PathVariable UUID postId){
        return likedPostService.getPostRate(postId);
    }


    @DeleteMapping("/delete-post")
    public void deletePost(@RequestParam UUID id){
        service.deletePost(id);
    }

}
