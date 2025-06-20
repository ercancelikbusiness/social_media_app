package com.ercancelik.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.requests.PostCreateRequest;
import com.ercancelik.questapp.requests.PostUpdateRequest;
import com.ercancelik.questapp.responses.PostResponse;
import com.ercancelik.questapp.services.PostService;

@RestController
@RequestMapping("/posts")

// özet RequestParam ?userId=1   şeklinde requestBody xml json requestVariable mappingde {} olcak   /2  gibi
//kısaca  bunlar kodda anlaşılsada teorik olarak araştır.

public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		
		this.postService = postService;
	}
	
/*	//frontend yapılınca burayı değiştirdim
	@GetMapping  //not: mapping anatasyonları altındaki metodu kapsıyor
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {  // request param bize gelen requestin içerisindeki parametreleri pars et ve sağında bulunan
																		//değişkenin içine at  yani düzenliyor springboot.. yani bu optional bir parametre geledebilir gelmeyedebilir gelirse userId ye göre postları getircek gelmezse /posts u çağırcak yani tüm postları getircek
        return postService.getAllPosts(userId);
    }
*/	
	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
		return postService.getAllPosts(userId);
	}

	
	@GetMapping("/{postId}")   // getAllPosts verilen şablonu parçalıyor bulup getiriyor burda direk postId veriliyor direk getircek
	                           // ek bilgi mesela bu tarayıcı url'sinde  ..../posts?userId=1 şeklinde gözükür(istenen id 1 ise)
	public PostResponse  getOnePost (@PathVariable Long  postId) {
		return postService.getOnePostByIdWithLikes(postId);
		
	}
	
	/*
	@PostMapping
	public Post createOnePost(@RequestBody Post newPost) { // fakat burda  komple bir json texti almamız gerekir yani post entitysinde 
		//neler vardı title userıd text gibi bunların hepsini vermemize gerek yok bu mantıklı olmaz hatalar eksikler ekstra kontroller gerekir
		//o yüzden bu ve devamındaki servisdeki buna ait metodu yorum satırına çeviriyorum ve burdan itibaren "requests" paketini oluşturuyorm
	    return postService.createOnePost(newPost);
	}
	*/
	
	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) { 
		return postService.createOnePost(newPostRequest);
	}
	
	@PutMapping("/{postId}")
	public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost) {   // postu update ederken userıd veya id sini update etmeyiz demekki ona göre metod yazcaz yani text ve title(bunları entity sınıfımıza göre alıyoruz unutma) almamız yeterli
		return postService.updateOnePostById(postId,updatePost);
	}
	
	@DeleteMapping("/{postId}")
	public void deleteOnePost(@PathVariable Long postId) {
		postService.deleteOnePostById(postId);
	}
	
	
	

}
