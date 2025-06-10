package com.ercancelik.questapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ercancelik.questapp.entities.User;
import com.ercancelik.questapp.responses.UserResponse;
import com.ercancelik.questapp.services.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController  // projemizin apisi burası
@RequestMapping("/users")  // ana path bu olacak yani url  /users..... diye gidcek alttaki metodlar devamını belirtir (user ile ilgililer için )

//örneğin postmana localhost:8080/users yazıcaz aşağıdaki metodlara göre localhost:8080/users/... şeklinde arama yapcaz

public class UserController {  // önce en alttaki yorum satırı olan usercontroller1 sonra  usercontroller2 sınıfını okuyabilirsin
	
	
	
private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers().stream().map(u -> new UserResponse(u)).toList();
	}
	
	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User newUser) {
		User user = userService.saveOneUser(newUser);
		if(user != null) 
			return new ResponseEntity<>(HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{userId}")
	public UserResponse getOneUser(@PathVariable Long userId) {
		User user = userService.getOneUserById(userId);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return new UserResponse(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
		User user = userService.updateOneUser(userId, newUser);
		if(user != null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		userService.deleteById(userId);
	}
	
	@GetMapping("/activity/{userId}")
	public List<Object> getUserActivity(@PathVariable Long userId) {
		return userService.getUserActivity(userId);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleUserNotFound() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* (2)
	private UserService userService; 
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	 //not: mapping anatasyonları altındaki metodu kapsıyor
	
	@GetMapping        // örneğin bu mappingde url yolu belirtilmemiş demekki localhost:8080/users yazıp get yapıcaz sadece
	public List<User> getAllUsers() {     
		return userService.getAllUsers();   //controllerin ilk halinde direk burda hazır metod vardı şimdi  önce servicedeki metoda yönlencez
											//yani mesela burdaki getAllUsers() metodu ile servicedeki metod ismi aynı olmak zorunda değil biz aynı
											//yaptık. hatta userService.getAllUsers(); ı burda yazdım üstüne tıklayıp service sınıfında
											//yarat dedim bu dediklerim diğerler metodlardada geçerli
	}
	
	@PostMapping      // burda ise  localhost:8080/users yine böyle olucak ama Post işlemi olacak ve postmanda body'e ekleme yapcaz formata uygun olarak
	public User createUser(@RequestBody User newUser) {
		return userService.saveOneUser(newUser);           
	}
	
	@GetMapping("/{userId}")   // burda postmanda  localhost:8080/users/1 gibi yazcaz sondaki id olcak 
	public User getOneUser(@PathVariable Long userId) {
	    // custom exception
	    return userService.getOneUserById(userId);
	}

	@PutMapping("/{userId}")
	public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
		return userService.updateOneUser(userId,newUser);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
	    userService.deleteById(userId);
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	/*// (1)
	private UserRepository userRepository;  
	
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
	
	
	
*/
}
