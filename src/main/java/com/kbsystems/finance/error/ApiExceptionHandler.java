package com.kbsystems.finance.error;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kbsystems.finance.error.ErrorResponse.ApiError;
import com.kbsystems.finance.service.exception.BusinessException;
import com.kbsystems.finance.service.exception.ResourceException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler {
	private final MessageSource apiErrorMessageSource;
	private static final String NO_AVAILABLE_MESSAGE = "no message available";
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
			
	public ApiExceptionHandler(MessageSource apiErrorMessageSource) {
		this.apiErrorMessageSource = apiErrorMessageSource;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception, Locale locale){
		List<ApiError> apiErros = exception.getBindingResult().getAllErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.map(code -> toApiError(code, locale))
			.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErros));
	}
	
	@ExceptionHandler(MismatchedInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(MismatchedInputException exception, Locale locale){
		ApiError apiError = this.toApiError("mismatched_input", locale, exception.getLocalizedMessage());
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiError);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(ResourceException.class)
	public ResponseEntity<ErrorResponse> handleResourceException(ResourceException exception, Locale locale){
		ApiError apiError = this.toApiError(exception.getCode(), locale, exception.getResourceName());
		ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), apiError);
		return ResponseEntity.status(exception.getStatus()).body(errorResponse);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale){
		ApiError apiError = this.toApiError(exception.getCode(), locale);
		ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), apiError);
		return ResponseEntity.status(exception.getStatus()).body(errorResponse);
	}
	
	public ApiError toApiError(String code, Locale locale, Object... args) {
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			LOG.error("Could not find message for {} code under {} locale", code, locale);
			message = NO_AVAILABLE_MESSAGE;
		}
		return new ApiError(code, message);
	}
}
