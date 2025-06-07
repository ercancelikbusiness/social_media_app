package com.ercancelik.questapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long long1);

}
