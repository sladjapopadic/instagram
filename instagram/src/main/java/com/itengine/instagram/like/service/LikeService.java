package com.itengine.instagram.like.service;

import com.itengine.instagram.like.model.Like;
import com.itengine.instagram.like.repository.LikeRepository;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.service.PostService;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.user.util.LoggedUser;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final PostService postService;
    private final LikeRepository likeRepository;

    public LikeService(PostService postService, LikeRepository likeRepository) {
        this.postService = postService;
        this.likeRepository = likeRepository;
    }

    public void likePost(Long postId) {
       Post post = postService.getById(postId);
       Like like = new Like();
       like.setPost(post);
       like.setUser(LoggedUser.getUser());
       likeRepository.save(like);
    }

    public void delete(Long userId) {
        likeRepository.deleteByUserId(userId);
    }
}
