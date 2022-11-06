package com.itengine.instagram.post.controller;

import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
       return new ResponseEntity<>(postService.getFollowedUsersPosts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestParam MultipartFile file, @RequestBody String caption) throws IOException {
        postService.createPost(file, caption);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
