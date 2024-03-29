package com.itengine.instagram.comment.repository;

import com.itengine.instagram.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment getById(Long id);

    @Transactional
    void deleteByUserId(Long userId);
}
