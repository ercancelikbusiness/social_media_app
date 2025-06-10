package com.ercancelik.questapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.CommentRepository;
import com.ercancelik.questapp.repos.LikeRepository;
import com.ercancelik.questapp.repos.PostRepository;
import com.ercancelik.questapp.repos.UserRepository;

@Service   // ilk başta  istekleri Controllerda yazarken direk Repos ile bağlantı kurarak yazmıştık ama arada Service katmanı olmalı zaten controllerde bunları yorum satırı yapacağım

public class UserService {
	
	 UserRepository userRepository;  // sağ tık- source- generate cons using fields- diyebiliriz
	LikeRepository likeRepository;
	CommentRepository commentRepository;
	PostRepository postRepository;

	public UserService(UserRepository userRepository, LikeRepository likeRepository, 
			CommentRepository commentRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}


	public List<User> getAllUsers() {   
		
		return userRepository.findAll();     //findAll save gibi metodlar Jpa nın kendi içinde hazır olarak vardır
	}

	public User saveOneUser(User newUser) {
		return userRepository.save(newUser);  //body'e yazılan kullanıcı(json doğru formatında) eklenmiş olacak yani burda dışardan alcaz
	}

	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	
	public User updateOneUser(Long userId, User newUser) {
	    Optional<User> user = userRepository.findById(userId);
	    if(user.isPresent()) {
	        User foundUser = user.get(); // optionaldan gelen get() metodur açıklaması PostServicedeki updateOnePostById metodunda mevcut
	        foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			foundUser.setAvatar(newUser.getAvatar()); // user entitysindekileri set ediyoruz yani
			userRepository.save(foundUser);
			return foundUser;
		}else
			return null;
	}

	public void deleteById(Long userId) {
		try {
		userRepository.deleteById(userId);
		}catch(EmptyResultDataAccessException e) { //user zaten yok, db'den empty result gelmiş
			System.out.println("User "+userId+" doesn't exist"); //istersek loglayabiliriz
		}
	}

	public User getOneUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}
	

	
	
}
