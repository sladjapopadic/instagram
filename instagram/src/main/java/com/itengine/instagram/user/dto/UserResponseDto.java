package com.itengine.instagram.user.dto;

import com.itengine.instagram.post.dto.PostDto;

import java.util.List;

public class UserResponseDto {

    private String username;
    private String description;
    private List<PostDto> posts;
    private List<UserProfileDto> followers;
    private List<UserProfileDto> following;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<UserProfileDto> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserProfileDto> followers) {
        this.followers = followers;
    }

    public List<UserProfileDto> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserProfileDto> following) {
        this.following = following;
    }
}
