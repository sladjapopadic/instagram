package com.itengine.instagram.comment.dto;

import java.util.List;

public class CommentResponseDto {

    private String username;
    private byte[] profileImage;
    private String text;
    private List<CommentResponseDto> replies;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CommentResponseDto> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentResponseDto> replies) {
        this.replies = replies;
    }
}
