package com.itengine.instagram.comment.service;

import com.itengine.instagram.comment.dto.CommentRequestDto;
import com.itengine.instagram.comment.dto.ReplyDto;
import com.itengine.instagram.comment.model.Comment;
import com.itengine.instagram.comment.repository.CommentRepository;
import com.itengine.instagram.post.model.Post;
import com.itengine.instagram.post.service.PostService;
import com.itengine.instagram.util.util.LoggedUser;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;

    public CommentService(PostService postService, CommentRepository commentRepository) {
        this.postService = postService;
        this.commentRepository = commentRepository;
    }

    public void comment(CommentRequestDto commentRequestDto) {
        Post post = postService.getById(commentRequestDto.getPostId());

        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setUser(LoggedUser.getUser());
        comment.setPost(post);

        commentRepository.save(comment);
    }

    public void reply(ReplyDto replyDto) {
        Comment parentComment = commentRepository.getById(replyDto.getCommentId());

        Comment comment = new Comment();
        comment.setText(replyDto.getText());
        comment.setUser(LoggedUser.getUser());
        comment.setComment(parentComment);

        commentRepository.save(comment);
    }

    public void delete(Long userId) {
        commentRepository.deleteByUserId(userId);
    }
}
