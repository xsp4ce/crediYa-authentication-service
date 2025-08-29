package com.crediya.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionMessagesTest {

	@Test
	void shouldHaveNonEmptyErrorMessages() {
		assertFalse(ExceptionMessages.VALIDATION_ERROR.isEmpty());
		assertFalse(ExceptionMessages.UNEXPECTED_ERROR.isEmpty());
		assertFalse(ExceptionMessages.DATABASE_ERROR.isEmpty());
		assertFalse(ExceptionMessages.FIELD_ALREADY_REGISTERED.isEmpty());
	}

	@Test
	void shouldNotBeInstantiable() {
		assertThrows(UnsupportedOperationException.class, ExceptionMessages::new);
	}

	@Test
	void shouldHaveExpectedMessages() {
		assertEquals("VALIDATION_ERROR", ExceptionMessages.VALIDATION_ERROR);
		assertEquals("Unexpected error", ExceptionMessages.UNEXPECTED_ERROR);
		assertEquals("Database error", ExceptionMessages.DATABASE_ERROR);
		assertEquals("Field is already registered", ExceptionMessages.FIELD_ALREADY_REGISTERED);
	}
}
