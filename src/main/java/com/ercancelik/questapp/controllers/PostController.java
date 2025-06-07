package com.ercancelik.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.services.PostService;

@RestController
@RequestMapping("/posts")

public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		
		this.postService = postService;
	}
	
	
	@GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {  // request param bize gelen requestin içerisindeki parametreleri pars et ve sağında bulunan
																		//değişkenin içine at  yani düzenliyor springboot.. yani bu optional bir parametre geledebilir gelmeyedebilir gelirse userId ye göre postları getircek gelmezse /posts u çağırcak yani tüm postları getircek
        return postService.getAllPosts(userId);
    }
	
	
	

}
