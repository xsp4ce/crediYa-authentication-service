package com.crediya.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		String message = "Test validation error";
		ValidationException exception = new ValidationException(message);

		assertEquals(message, exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	void shouldCreateExceptionWithMessageAndCause() {
		String message = "Test validation error";
		ValidationException exception = new ValidationException(message);

		assertEquals(message, exception.getMessage());
	}

	@Test
	void shouldBeRuntimeException() {
		ValidationException exception = new ValidationException("test");
		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	void shouldCreateExceptionWithNullMessage() {
		ValidationException exception = new ValidationException(null);
		assertNull(exception.getMessage());
	}
}
