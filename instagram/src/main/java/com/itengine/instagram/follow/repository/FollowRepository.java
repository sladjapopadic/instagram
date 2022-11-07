package com.itengine.instagram.follow.repository;

import com.itengine.instagram.follow.model.Follow;
import com.itengine.instagram.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowFromAndFollowTo(User followFrom, User followTo);

    @Transactional
    void deleteByFollowFromAndFollowTo(User followFrom, User followTo);

    @Transactional
    void deleteByFollowFromIdOrFollowToId(Long followFrom, Long followTo);

    @Query("SELECT follow FROM Follow follow " +
            "WHERE follow.followFrom.id IN ( " +
            "SELECT follow.followTo.id FROM follow " +
            "WHERE follow.followFrom.id = :userId) " +
            "AND follow.followTo.id NOT IN ( " +
            "SELECT follow.followTo.id FROM follow " +
            "WHERE follow.followFrom.id = :userId)")
    List<Follow> findSuggestions(Long userId);
}
