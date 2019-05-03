package com.kbsystems.finance.service.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BusinessException{
	public UserAlreadyExistsException() {
		super(HttpStatus.BAD_REQUEST, "user_already_exists");
	}

	private static final long serialVersionUID = 1L;
	
}
