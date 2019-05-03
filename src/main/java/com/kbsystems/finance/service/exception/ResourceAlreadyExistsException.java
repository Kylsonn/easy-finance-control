package com.kbsystems.finance.service.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends ResourceException{
	public ResourceAlreadyExistsException(String resource) {
		super(HttpStatus.BAD_REQUEST, "resource_already_exists", resource);
	}

	private static final long serialVersionUID = 1L;
	
}
