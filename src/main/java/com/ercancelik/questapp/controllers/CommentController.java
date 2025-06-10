package com.ercancelik.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ercancelik.questapp.entities.Comment;
import com.ercancelik.questapp.requests.CommentCreateRequest;
import com.ercancelik.questapp.services.CommentService;

@RestController
@RequestMapping("/comments")

public class CommentController {
	
	
	private CommentService commentService;
	
	public CommentController (CommentService commentService) {
		
		this.commentService=commentService;
	}
	
	
	@GetMapping
	public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
		return commentService.getAllCommentsWithParam(userId, postId);
	}
	
	@GetMapping("/{commentId}")
	public Comment getOneComment(@PathVariable Long commentId) {
		return commentService.getOneCommentById(commentId);
	}
	
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest request) {
		return commentService.createOneComment(request);
	}

}
