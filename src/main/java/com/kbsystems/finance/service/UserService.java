package com.kbsystems.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbsystems.finance.domain.User;
import com.kbsystems.finance.repository.UserRepository;
import com.kbsystems.finance.service.exception.PasswordInvalidException;
import com.kbsystems.finance.service.exception.ResourceAlreadyExistsException;
import com.kbsystems.finance.service.exception.ResourceNotFoundException;

@Service
public class UserService {
	private static final String PASSWORD_RULE = ".{8,30}";
	private UserRepository userRepository;
	
	public UserService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User create(User user) {
		verifyIfUserExistsByUsername(user);
		checkPassword(user.getPassword());
		return userRepository.insert(user);
	}
	
	public User update(User user) {
		verifyIfUserDoesntExistById(user.getId());
		verifyIfUserExistsByUsername(user);
		checkPassword(user.getPassword());
		return userRepository.save(user);
	}
	
	public void delete(String id){
		verifyIfUserDoesntExistById(id);
		userRepository.deleteById(id);
	}
	
	private void verifyIfUserExistsByUsername(User user) {
		User userByUsername = userRepository.findByUsername(user.getUsername()).orElse(null);
		if (userByUsername != null && !userByUsername.equals(user) 
				|| (user.isNew() && userByUsername != null)) {
			throw new ResourceAlreadyExistsException(User.class.getSimpleName());
		}
	}
	
	private void verifyIfUserDoesntExistById(String id) {
		if (!userRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException(User.class.getSimpleName());
		}
	}
	
	private void checkPassword(String password) {
		if (!password.matches(PASSWORD_RULE)) {
			throw new PasswordInvalidException();
		}
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
