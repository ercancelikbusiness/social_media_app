package com.ercancelik.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.PostRepository;
import com.ercancelik.questapp.requests.PostCreateRequest;

@Service

public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	
	public PostService (PostRepository postRepository,UserService userService) {
		this.postRepository=postRepository;
		this.userService = userService;
	}

	public List<Post> getAllPosts(Optional<Long> userId) {    // bunun açıklaması postRepository'de oku  !!!! çünkü burdaki tüm metodları
															 // farkettiysen PostRepository'de tanımlamadık
		if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
	
/*
	public Post createOnePost(Post newPost) {    //controllerda neden yoruma çevirdim anlatıyor
		return postRepository.save(newPost);
	}
*/

	public Post createOnePost(PostCreateRequest newPostRequest) {  // bu metodda   gelen requestin içersindeki userıd databasede varmı  diye
		//validation yapcaz bunuda user servisi kullanacağız yani bu servisde başka bir servise bağlanıcağız (repoyada bağlanabilirdik 
		//basit bir hazır metodu varsa... ama sistematik olmaz) o yüzden private UserService userService; oluşturup constructor umuza ekledik
		// requests paketine göre postcreaterequest teki alanları  json body si olarak postlayabiliriz
		//ve  gönderince  postman dönütünde ilgisiz user alanı çıkmadı ancak veritabanındaki tabloya tam yerleşme yapıldı mesela userId yi
		//tanımladı
		User user = userService.getOneUser(newPostRequest.getUserId());
		if(user == null)
			return null;
		Post toSave = new Post();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setUser(user);
		return postRepository.save(toSave);
	}
	

	
	

}
