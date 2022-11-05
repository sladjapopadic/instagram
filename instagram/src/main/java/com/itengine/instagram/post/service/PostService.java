package com.itengine.instagram.post.service;

import com.itengine.instagram.comment.service.CommentService;
import com.itengine.instagram.comment.util.CommentConverter;
import com.itengine.instagram.like.model.Like;
import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.repository.PostRepository;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.user.util.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final UserService userService;
    private final CommentConverter commentConverter;
    private final PostRepository postRepository;

    public PostService(UserService userService, CommentConverter commentConverter, PostRepository postRepository) {
        this.userService = userService;
        this.commentConverter = commentConverter;
        this.postRepository = postRepository;
    }

    private PostDto convertToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUsername(post.getUser().getUsername());
        postDto.setProfileImage(post.getUser().getImage());
        postDto.setImage(post.getImage());
        postDto.setCaption(post.getCaption());
        postDto.setComments(commentConverter.convertToCommentDtos(post.getComments()));
        postDto.setLikes(post.getLikes().size());

        return postDto;
    }

    public List<PostDto> getFollowedUsersPosts() {
        List<User> followedUsers = userService.getFollowedUsers(LoggedUser.getId());
        List<PostDto> posts = new ArrayList<>();
        for (User user : followedUsers) {
            for (Post post : user.getPosts()) {
                posts.add(convertToPostDto(post));
            }
        }
        return posts;
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }
}
