package com.ercancelik.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.Comment;
import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.CommentRepository;
import com.ercancelik.questapp.requests.CommentCreateRequest;

@Service

public class CommentService {
	
	
	
	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, UserService userService, 
			PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}
	

	public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		
		if(userId.isPresent() && postId.isPresent()) {
			return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			return commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			return commentRepository.findByPostId(postId.get());
		}else
			return  commentRepository.findAll();
		
	}


	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}


	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
		
			return commentRepository.save(commentToSave);
		}else		
			return null;
	}
	
	
	

}
