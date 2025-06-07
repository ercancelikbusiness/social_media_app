package com.ercancelik.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.repos.PostRepository;

@Service

public class PostService {
	
	private PostRepository postRepository;
	
	public PostService (PostRepository postRepository) {
		this.postRepository=postRepository;
	}

	public List<Post> getAllPosts(Optional<Long> userId) {
		if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }
	

}
