package com.itengine.instagram.follow.repository;

import com.itengine.instagram.follow.model.Follow;
import com.itengine.instagram.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowFromAndFollowTo(User followFrom, User followTo);
    void deleteByFollowFromAndFollowTo(User followFrom, User followTo);
}
