package com.ercancelik.questapp.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.Post;
import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.PostRepository;
import com.ercancelik.questapp.requests.PostCreateRequest;
import com.ercancelik.questapp.requests.PostUpdateRequest;
import com.ercancelik.questapp.responses.LikeResponse;
import com.ercancelik.questapp.responses.PostResponse;

@Service

public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	private LikeService likeService;
	
	public PostService (PostRepository postRepository,UserService userService) {
		this.postRepository=postRepository;
		this.userService = userService;
	}
	@Autowired
	public void setLikeService(@Lazy LikeService likeService) {
		this.likeService = likeService;
	}
	
/*//frontend yapınca burayı değiştirdim
	public List<Post> getAllPosts(Optional<Long> userId) {    // bunun açıklaması postRepository'de oku  !!!! çünkü burdaki tüm metodları
															 // farkettiysen PostRepository'de tanımlamadık
		if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }
 */
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent()) {
			 list = postRepository.findByUserId(userId.get());
		}else
			list = postRepository.findAll();
		return list.stream().map(p -> { 
			List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p, likes);}).collect(Collectors.toList());
	}

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
	
/*
	public Post createOnePost(Post newPost) {    //controllerda neden yoruma çevirdim anlatıyor
		return postRepository.save(newPost);
	}
*/
	public PostResponse getOnePostByIdWithLikes(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
		return new PostResponse(post, likes); 
	}

	public Post createOnePost(PostCreateRequest newPostRequest) {  // bu metodda   gelen requestin içersindeki userıd databasede varmı  diye
		//validation yapcaz bunuda user servisi kullanacağız yani bu servisde başka bir servise bağlanıcağız (repoyada bağlanabilirdik 
		//basit bir hazır metodu varsa... ama sistematik olmaz) o yüzden private UserService userService; oluşturup constructor umuza ekledik
		// requests paketine göre postcreaterequest teki alanları  json body si olarak postlayabiliriz
		//ve  gönderince  postman dönütünde ilgisiz user alanı çıkmadı ancak veritabanındaki tabloya tam yerleşme yapıldı mesela userId yi
		//tanımladı
		User user = userService.getOneUserById(newPostRequest.getUserId());
		if(user == null)
			return null;
		Post toSave = new Post();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setUser(user);
		toSave.setCreateDate(new Date());
		return postRepository.save(toSave);
	}

	public Post updateOnePostById(Long postId,PostUpdateRequest updatePost) {
		Optional<Post> post = postRepository.findById(postId);  // yani içinde Post türünde bir postId varmı yokmu varsa post değişkeni veriyi alır  yoksa almaz
		//post değişkeni, türü Optional<Post> olan bir nesnedir. post aslında  “kabuk”tur. İçinde Post nesnesi olabilir veya olmayabilir.
		//post.get() dediğimizde, artık elimizde gerçekten bir Post nesnesi var post.get() buradaki .get() metodu, Optional sınıfına ait bir metottur.
		//Yani bu, java.util.Optional<T> sınıfının get() metodudur.Post entity'sindeki get metotlarıyla hiçbir ilgisi yoktur.
		//ayrıca direk  post.get().setText ... diyede gidebilirdik ama oda kod okunabilirliğini bozar ve  optionalın sonucuna göre
		//hata verebilir. kısaca aşağıdaki post.get() niye yaptık bunun cevabını verdim eğer postId ye göre bir Post varsa  o zaten post
		//içindedir fakat onu get etmemiz önemlidir
		if(post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(updatePost.getText());
			toUpdate.setTitle(updatePost.getTitle());
			//toUpdate.setText(updatePost.getText()); burdaki get set ler nereden geliyor ?  toUpdate deki set Post sınıfından gelir
			//yani ordaki lombok sayesindeki hazır get set metodlarından updatePost daki get ise  PostUpdateRequest sınıfındaki  getden gelir
			//yani aslında  kullanıcının put isteğinden gelir controllerda kullanıcıdan istemiştik sonucta bu metod veri güncelleme idi
			postRepository.save(toUpdate);
			return toUpdate; // bu genelde gereklidir yapmasakda olurdu ama belki ilerde metodu farklı amaçla kullancaz verisi gerekebilir ayrıca postmanda cevabı görelim
		}
		return null;
	}

	public void deleteOnePostById(Long postId) {
		postRepository.deleteById(postId);
		
	}
	
	

	
	

}
