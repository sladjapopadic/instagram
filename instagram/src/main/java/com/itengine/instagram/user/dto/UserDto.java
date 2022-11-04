package com.itengine.instagram.user.dto;

import com.itengine.instagram.followers.model.Followers;
import com.itengine.instagram.post.model.Post;

import java.util.List;

public class UserDto {

    private List<Post> posts;
    private List<Followers> followers;
    private List<Followers> followings;
    private String description;
    private byte[] image;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Followers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Followers> followers) {
        this.followers = followers;
    }

    public List<Followers> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Followers> followings) {
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
