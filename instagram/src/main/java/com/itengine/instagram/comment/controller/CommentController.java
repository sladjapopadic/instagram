package com.itengine.instagram.comment.controller;

import com.itengine.instagram.comment.dto.CommentRequestDto;
import com.itengine.instagram.comment.dto.ReplyDto;
import com.itengine.instagram.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> comment(@RequestBody CommentRequestDto commentRequestDto) {
        commentService.comment(commentRequestDto);
        return null;
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> reply(@RequestBody ReplyDto replyDto) {
        commentService.reply(replyDto);
        return null;
    }
}
