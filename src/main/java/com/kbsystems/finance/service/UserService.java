package com.kbsystems.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbsystems.finance.domain.User;
import com.kbsystems.finance.repository.UserRepository;
import com.kbsystems.finance.service.exception.UserAlreadyExistsException;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public User create(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException();
		}
		return userRepository.insert(user);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
