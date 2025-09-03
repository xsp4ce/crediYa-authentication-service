package com.crediya.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		String message = "Validation failed";
		ValidationException exception = new ValidationException(message);

		assertEquals(message, exception.getMessage());
	}

	@Test
	void shouldCreateExceptionWithNullMessage() {
		ValidationException exception = new ValidationException(null);

		assertNull(exception.getMessage());
	}

	@Test
	void shouldBeRuntimeException() {
		ValidationException exception = new ValidationException("test");

		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	void shouldBeThrownAndCaught() {
		String message = "Test validation error";

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			throw new ValidationException(message);
		});

		assertEquals(message, thrown.getMessage());
	}
}
