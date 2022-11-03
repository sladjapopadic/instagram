package com.itengine.instagram.post.dto;

import com.itengine.instagram.user.model.User;

import java.time.LocalDate;

public class PostDto {

    private User user;

    private LocalDate datePosted;

    private String caption;

    private byte[] image;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
