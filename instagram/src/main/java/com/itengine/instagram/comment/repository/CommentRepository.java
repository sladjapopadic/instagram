package com.itengine.instagram.comment.repository;

import com.itengine.instagram.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment getById(Long id);

    void deleteByUserId(Long userId);

}
