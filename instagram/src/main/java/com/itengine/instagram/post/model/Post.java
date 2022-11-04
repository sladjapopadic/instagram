package com.itengine.instagram.post.model;

import com.itengine.instagram.user.model.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_posted", nullable = false)
    private LocalDate datePosted;

    @Column(name = "caption")
    private String caption;

    @Column(name = "image", nullable = false)
    private byte[] image;
}
