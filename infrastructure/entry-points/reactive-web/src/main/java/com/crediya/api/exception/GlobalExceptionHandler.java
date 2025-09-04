package com.crediya.api.exception;

import com.crediya.model.user.exceptions.BusinessException;
import com.crediya.model.user.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<CustomErrorResponse> handleValidation(ValidationException ex) {
		return ResponseEntity.badRequest().body(new CustomErrorResponse("VALIDATION_ERROR", ex.getMessage()));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<CustomErrorResponse> handleBusiness(BusinessException ex) {
		return ResponseEntity.internalServerError().body(new CustomErrorResponse("BUSINESS_ERROR", ex.getMessage()));
	}
}
