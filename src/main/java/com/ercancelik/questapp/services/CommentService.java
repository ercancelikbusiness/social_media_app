package com.ercancelik.questapp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.Comment;
import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.CommentRepository;
import com.ercancelik.questapp.requests.CommentCreateRequest;
import com.ercancelik.questapp.requests.CommentUpdateRequest;
import com.ercancelik.questapp.responses.CommentResponse;

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
	

	public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			comments = commentRepository.findByPostId(postId.get());
		}else
			comments = commentRepository.findAll();
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}

	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}


	public Comment createOneComment(CommentCreateRequest request) {
		//not Her yorum, mutlaka bir kullanıcıya ve bir posta  sahip olmalı yani önceden var olan bir user ve post olmalı
		//burda yeni yorum yaratıyoruz. yorumuna yatan bir kullanıcı sistemde var olmalı
		//yeni bir kullanıcı bir yorum atcaksa zaten önceden kayıt olmalı yani forumlarda kayıt olmadan yorum yazamayız
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if(user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
			commentToSave.setCreateDate(new Date());
			return commentRepository.save(commentToSave);
		}else		
			return null;
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return commentRepository.save(commentToUpdate);
		}else
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}
	
	
	
	

}
