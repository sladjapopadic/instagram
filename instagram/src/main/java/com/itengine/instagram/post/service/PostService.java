package com.itengine.instagram.post.service;

import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.repository.PostRepository;
import com.itengine.instagram.post.util.PostConverter;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.user.util.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(UserService userService, PostRepository postRepository, PostConverter postConverter) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    public List<PostDto> getFollowedUsersPosts() {
        List<User> followedUsers = userService.getFollowedUsers(LoggedUser.getId());
        List<PostDto> posts = new ArrayList<>();
        for (User user : followedUsers) {
            posts.addAll(postConverter.convertToPostDtos(user.getPosts()));
        }
        return posts;
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }
}
