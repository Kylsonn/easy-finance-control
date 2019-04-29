package com.kbsystems.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbsystems.finance.domain.User;
import com.kbsystems.finance.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User create(User user) {
		return userRepository.insert(user);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
}
