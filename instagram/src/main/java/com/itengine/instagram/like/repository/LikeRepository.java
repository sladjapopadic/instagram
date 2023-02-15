package com.itengine.instagram.like.repository;

import com.itengine.instagram.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Transactional
    void deleteByUserId(Long userId);

    @Transactional
    void deleteByUserIdAndPostId(Long userId, Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

}
