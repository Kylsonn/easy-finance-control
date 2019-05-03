package com.kbsystems.finance.service.exception;

import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public PasswordInvalidException() {
		super(HttpStatus.BAD_REQUEST, "invalid_password");
	}

}
