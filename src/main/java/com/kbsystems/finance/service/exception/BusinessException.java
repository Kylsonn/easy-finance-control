package com.kbsystems.finance.service.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus status;
	private final String code;
	
	public BusinessException(HttpStatus status, String code) {
		super();
		this.status = status;
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}
	
}
