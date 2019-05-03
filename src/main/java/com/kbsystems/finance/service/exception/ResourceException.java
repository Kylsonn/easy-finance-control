package com.kbsystems.finance.service.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends BusinessException {
	private static final long serialVersionUID = 1L;

	private final String resourceName;
	public ResourceException(HttpStatus status, String code, String resourceName) {
		super(status, code);
		this.resourceName = resourceName;
	}
	
	public String getResourceName() {
		return resourceName;
	}	
}
