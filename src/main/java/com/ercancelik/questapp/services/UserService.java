package com.ercancelik.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.repos.UserRepository;

@Service   // ilk başta  istekleri Controllerda yazarken direk Repos ile bağlantı kurarak yazmıştık ama arada Service katmanı olmalı zaten controllerde bunları yorum satırı yapacağım

public class UserService {
	
	private UserRepository userRepository;  // sağ tık- source- generate cons using fields- diyebiliriz

	public UserService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {   
		
		return userRepository.findAll();     //findAll save gibi metodlar Jpa nın kendi içinde hazır olarak vardır
	}

	public User saveOneUser(User newUser) {
		return userRepository.save(newUser);  //body'e yazılan kullanıcı(json doğru formatında) eklenmiş olacak yani burda dışardan alcaz
	}

	public User getOneUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateOneUser(Long userId, User newUser) {
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

	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}


	
	
}
