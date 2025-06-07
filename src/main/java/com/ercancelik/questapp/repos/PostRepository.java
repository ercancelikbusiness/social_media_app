package com.ercancelik.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
