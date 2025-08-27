package com.crediya.api.exception;

import com.crediya.model.user.exceptions.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<CustomErrorResponse> handleValidation(ValidationException ex) {
		log.error("Validation error: {}", ex.getMessage());
		return ResponseEntity.badRequest().body(new CustomErrorResponse("VALIDATION_ERROR", ex.getMessage()));
	}
}
