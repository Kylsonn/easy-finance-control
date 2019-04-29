package com.kbsystems.finance.error;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kbsystems.finance.error.ErrorResponse.ApiError;

@RestControllerAdvice
public class ApiExceptionHandler {
	private final MessageSource apiErrorConfig;
	private static final String NO_AVAILABLE_MESSAGE = "no message available";
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
			
	public ApiExceptionHandler(MessageSource apiMessageSource) {
		this.apiErrorConfig = apiMessageSource;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception, Locale locale){
		List<ApiError> apiErros = exception.getBindingResult().getAllErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.map(code -> toApiError(code, locale))
			.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErros));
	}
	
	private ApiError toApiError(String code, Locale locale) {
		String message;
		try {
			message = apiErrorConfig.getMessage(code, null, locale);
		} catch (NoSuchMessageException e) {
			LOG.error("Could not find message for {} code under {} locale", code, locale);
			message = NO_AVAILABLE_MESSAGE;
		}
		return new ApiError(code, message);
	}
}
