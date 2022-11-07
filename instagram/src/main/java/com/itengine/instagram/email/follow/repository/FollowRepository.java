package com.itengine.instagram.email.follow.repository;

import com.itengine.instagram.email.follow.model.Follow;
import com.itengine.instagram.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowFromAndFollowTo(User followFrom, User followTo);

    void deleteByFollowFromAndFollowTo(User followFrom, User followTo);

    void deleteByFollowFromIdOrFollowToId(Long followFrom, Long followTo);

    @Query("SELECT follow FROM Follow follow " +
            "WHERE follow.followFrom IN ( " +
            "SELECT follow.followTo FROM follow " +
            "WHERE follow.followFrom = :userId) " +
            "AND follow.followTo NOT IN ( " +
            "SELECT follow.followTo FROM follow " +
            "WHERE follow.followFrom = :userId)")
    List<Follow> findSuggestions(Long userId);
}
