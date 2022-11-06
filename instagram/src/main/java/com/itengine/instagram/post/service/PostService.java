package com.itengine.instagram.post.service;

import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.repository.PostRepository;
import com.itengine.instagram.post.util.PostConverter;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.user.util.LoggedUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
        List<Post> posts = new ArrayList<>();

        for (User user : followedUsers) {
            posts.addAll(user.getPosts());
        }

        return postConverter.convertToSortedPostDtos(posts);
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public void delete(Long userId) {
        postRepository.deleteByUserId(userId);
    }

    public void createPost(MultipartFile file, String caption) throws IOException {
        Post post = new Post();
        post.setCaption(caption);
        post.setUser(LoggedUser.getUser());
        post.setImage(file.getBytes());
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
