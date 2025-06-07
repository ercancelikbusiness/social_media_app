package com.ercancelik.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
