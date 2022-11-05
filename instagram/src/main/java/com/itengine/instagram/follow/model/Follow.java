package com.itengine.instagram.follow.model;

import com.itengine.instagram.user.model.User;

import javax.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follow_from", nullable = false)
    private User followFrom;

    @ManyToOne
    @JoinColumn(name = "follow_to", nullable = false)
    private User followTo;

    public Follow() {

    }

    public Follow(User followFrom, User followTo) {
        this.followFrom = followFrom;
        this.followTo = followTo;
    }

    public User getFollowFrom() {
        return followFrom;
    }

    public User getFollowTo() {
        return followTo;
    }
}
