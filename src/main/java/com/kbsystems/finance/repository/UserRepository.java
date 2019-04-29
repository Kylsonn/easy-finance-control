package com.kbsystems.finance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kbsystems.finance.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
}
