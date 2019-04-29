package com.kbsystems.finance.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private final int statusCode;
	private final List<ApiError> errors;

	private ErrorResponse(int statusCode, List<ApiError> errors) {
		this.statusCode = statusCode;
		this.errors = errors;
	}
	
	static ErrorResponse of(HttpStatus status, List<ApiError> errors){
		return new ErrorResponse(status.value(), errors);
	}
	static ErrorResponse of(HttpStatus status, ApiError error){
		return of(status, Collections.singletonList(error));
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public List<ApiError> getErrors() {
		return errors;
	}


	static class ApiError {
		private final String code;
		private final String message;

		public ApiError(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}