package com.itengine.instagram.post.controller;

import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getFollowedUsersPosts() {
        postService.getFollowedUsersPosts();
        return null;
    }
}
