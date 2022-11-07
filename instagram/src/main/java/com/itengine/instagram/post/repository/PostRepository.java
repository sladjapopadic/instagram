package com.itengine.instagram.post.repository;

import com.itengine.instagram.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getById(Long id);

    @Transactional
    void deleteByUserId(Long userId);
}
