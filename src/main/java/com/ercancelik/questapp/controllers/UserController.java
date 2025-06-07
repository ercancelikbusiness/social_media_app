package com.ercancelik.questapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController  // UserRepository tanımlamasında açıkladım
@RequestMapping("/users")  // ana path bu olacak yani url  /users..... diye gidcek alttaki metodlar devamını belirtir (user ile ilgililer için )

//örneğin postmana localhost:8080/users yazıcaz aşağıdaki metodlara göre localhost:8080/users/... şeklinde arama yapcaz

public class UserController {
	
	private UserRepository userRepository;   //bunun olayı restcontroller anatosonu sayesinde userRepositoryyi newleyip örneğini oluşturmaya gerek kalmıyor ilgili sınıfları metodları refansını otomatik hallediyor
	
	public UserController(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@GetMapping        // örneğin bu mappingde url yolu belirtilmemiş demekki localhost:8080/users yazıp get yapıcaz sadece
	public List<User> getAllUsers() {     
		return userRepository.findAll();  //findAll gibi metodlar Jpa nın kendi içinde hazır olarak vardır
	}
	
	@PostMapping      // burda ise  localhost:8080/users yine böyle olucak ama Post işlemi olacak ve postmanda body'e ekleme yapcaz formata uygun olarak
	public User createUser(@RequestBody User newUser) {
		return userRepository.save(newUser);             //body'e yazılan kullanıcı(json doğru formatında) eklenmiş olacak yani burda dışardan alcaz
	}
	
	@GetMapping("/{userId}")   // burda postmanda  localhost:8080/users/1 gibi yazcaz sondaki id olcak 
	public User getOneUser(@PathVariable Long userId) {
	    // custom exception
	    return userRepository.findById(userId).orElse(null);
	}

	@PutMapping("/{userId}")
	public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
	    Optional<User> user = userRepository.findById(userId);
	    if(user.isPresent()) {
	        User foundUser = user.get();
	        foundUser.setUserName(newUser.getUserName());
	        foundUser.setPassword(newUser.getPassword());
	        userRepository.save(foundUser);
	        return foundUser;
	    } else {
	        return null;
	    }
	}
	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
	    userRepository.deleteById(userId);
	}
	
	
	

}
