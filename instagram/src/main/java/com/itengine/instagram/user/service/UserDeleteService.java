package com.itengine.instagram.user.service;

import com.itengine.instagram.comment.service.CommentService;
import com.itengine.instagram.follow.service.FollowService;
import com.itengine.instagram.like.service.LikeService;
import com.itengine.instagram.post.service.PostService;
import com.itengine.instagram.user.repository.UserRepository;
import com.itengine.instagram.user.util.LoggedUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDeleteService {

    private final FollowService followService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostService postService;
    private final UserRepository userRepository;

    public UserDeleteService(FollowService followService, LikeService likeService, CommentService commentService, PostService postService, UserRepository userRepository) {
        this.followService = followService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void delete() {
        Long userId = LoggedUser.getId();

        likeService.delete(userId);
        followService.delete(userId);
        commentService.delete(userId);
        postService.delete(userId);
        userRepository.deleteById(userId);
    }
}
