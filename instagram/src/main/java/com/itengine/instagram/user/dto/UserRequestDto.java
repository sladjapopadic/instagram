package com.itengine.instagram.user.dto;

import com.itengine.instagram.follow.model.Follow;
import com.itengine.instagram.post.model.Post;

import java.util.List;

public class UserRequestDto {

    private List<Post> posts;
    private List<Follow> followers;
    private List<Follow> followings;
    private String description;
    private byte[] image;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follow> followers) {
        this.followers = followers;
    }

    public List<Follow> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Follow> followings) {
        this.followings = followings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
