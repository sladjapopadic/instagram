package com.itengine.instagram.like.repository;

import com.itengine.instagram.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
