package com.itengine.instagram.comment.util;

import com.itengine.instagram.comment.dto.CommentResponseDto;
import com.itengine.instagram.comment.model.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverter {

    public CommentResponseDto convertToCommentDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setUsername(comment.getUser().getUsername());
        commentResponseDto.setProfileImage(comment.getUser().getImage());
        commentResponseDto.setText(comment.getText());
        List<CommentResponseDto> replies = new ArrayList<>();
        for (Comment reply : comment.getReplies()) {
            replies.add(convertToCommentDto(reply));
        }
        commentResponseDto.setReplies(replies);
        return commentResponseDto;
    }

    public List<CommentResponseDto> convertToCommentDtos(List<Comment> comments) {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseDtos.add(convertToCommentDto(comment));
        }
        return commentResponseDtos;
    }


}
