package com.itengine.instagram.like.model;

import com.itengine.instagram.comment.model.model.Post;
import com.itengine.instagram.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
