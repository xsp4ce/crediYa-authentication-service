package com.crediya.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionMessagesTest {

	@Test
	void shouldHaveExpectedErrorMessages() {
		assertEquals("VALIDATION_ERROR", ExceptionMessages.VALIDATION_ERROR);
		assertEquals("Unexpected error", ExceptionMessages.UNEXPECTED_ERROR);
		assertEquals("Database error", ExceptionMessages.DATABASE_ERROR);
		assertEquals("Field is already registered", ExceptionMessages.FIELD_ALREADY_REGISTERED);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, ExceptionMessages::new);
	}

	@Test
	void shouldHaveNonEmptyMessages() {
		assertFalse(ExceptionMessages.VALIDATION_ERROR.isEmpty());
		assertFalse(ExceptionMessages.UNEXPECTED_ERROR.isEmpty());
		assertFalse(ExceptionMessages.DATABASE_ERROR.isEmpty());
		assertFalse(ExceptionMessages.FIELD_ALREADY_REGISTERED.isEmpty());
	}

	@Test
	void shouldHaveConsistentMessageStyles() {
		assertEquals("VALIDATION_ERROR", ExceptionMessages.VALIDATION_ERROR);
		assertTrue(ExceptionMessages.VALIDATION_ERROR.matches("^[A-Z_]+$"));

		assertTrue(ExceptionMessages.UNEXPECTED_ERROR.matches("^[A-Z].*"));
		assertTrue(ExceptionMessages.DATABASE_ERROR.matches("^[A-Z].*"));
		assertTrue(ExceptionMessages.FIELD_ALREADY_REGISTERED.matches("^[A-Z].*"));
	}

	@Test
	void shouldHaveUniqueMessages() {
		assertNotEquals(ExceptionMessages.VALIDATION_ERROR, ExceptionMessages.UNEXPECTED_ERROR);
		assertNotEquals(ExceptionMessages.VALIDATION_ERROR, ExceptionMessages.DATABASE_ERROR);
		assertNotEquals(ExceptionMessages.VALIDATION_ERROR, ExceptionMessages.FIELD_ALREADY_REGISTERED);
		assertNotEquals(ExceptionMessages.UNEXPECTED_ERROR, ExceptionMessages.DATABASE_ERROR);
		assertNotEquals(ExceptionMessages.UNEXPECTED_ERROR, ExceptionMessages.FIELD_ALREADY_REGISTERED);
		assertNotEquals(ExceptionMessages.DATABASE_ERROR, ExceptionMessages.FIELD_ALREADY_REGISTERED);
	}
}
