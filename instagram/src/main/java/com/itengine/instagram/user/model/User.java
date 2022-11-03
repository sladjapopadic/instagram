package com.itengine.instagram.user.model;

import com.itengine.instagram.followers.model.Followers;
import com.itengine.instagram.post.model.Post;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "instagram_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "follower")
    private List<Followers> followers;

    @OneToMany(mappedBy = "following")
    private List<Followers> following;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private byte[] image;
}
