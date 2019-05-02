package com.kbsystems.finance.error;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kbsystems.finance.error.ErrorResponse.ApiError;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GenericExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
	@Autowired
	private ApiExceptionHandler apiExcetionHandler;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, Locale locale){
		ApiError apiError = apiExcetionHandler.toApiError("generic_error", locale);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, apiError);
		LOG.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
