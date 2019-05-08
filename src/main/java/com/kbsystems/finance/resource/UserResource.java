package com.kbsystems.finance.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kbsystems.finance.domain.User;
import com.kbsystems.finance.event.NewResourceEvent;
import com.kbsystems.finance.service.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<User>> all(){
		List<User> users = userService.findAll();
		return !users.isEmpty() ?  ResponseEntity.ok(users)
				: ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable String id){
		Optional<User> user = userService.findById(id);
		return user.isPresent() ?  ResponseEntity.ok(user.get())
				: ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody User user, HttpServletResponse response){
		User userSaved = userService.create(user);
		publisher.publishEvent(new NewResourceEvent(this, response, userSaved.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		userService.delete(id);
	}
	
	@PutMapping("/{id}")
	public User update(@PathVariable String id, @Valid @RequestBody User user) {
		user.setId(id);
		return userService.update(user);
	}
	
}
