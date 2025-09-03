package com.crediya.model.user.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessagesTest {

	@Test
	void shouldHaveExpectedDatabaseLogMessages() {
		assertEquals("Saving user in the database", LogMessages.SAVING_USER_IN_DATABASE);
		assertEquals("Validating if email is already registered in the database",
		 LogMessages.VALIDATING_EMAIL_IN_DATABASE);
		assertEquals("Validating if document number is already registered in the database",
		 LogMessages.VALIDATING_DOCUMENT_IN_DATABASE);
	}

	@Test
	void shouldHaveExpectedPathLogMessages() {
		assertEquals("Consuming path: /api/v1/users", LogMessages.CONSUMING_PATH_USERS);
		assertEquals("Consuming path: /api/v1/login", LogMessages.CONSUMING_PATH_LOGIN);
		assertEquals("Consuming path: /api/v1/document", LogMessages.CONSUMING_PATH_DOCUMENT);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, LogMessages::new);
	}

	@Test
	void shouldHaveConsistentMessageFormats() {
		assertTrue(LogMessages.CONSUMING_PATH_USERS.startsWith("Consuming path: "));
		assertTrue(LogMessages.CONSUMING_PATH_LOGIN.startsWith("Consuming path: "));
		assertTrue(LogMessages.CONSUMING_PATH_DOCUMENT.startsWith("Consuming path: "));
	}

	@Test
	void shouldHaveNonEmptyMessages() {
		assertFalse(LogMessages.SAVING_USER_IN_DATABASE.isEmpty());
		assertFalse(LogMessages.VALIDATING_EMAIL_IN_DATABASE.isEmpty());
		assertFalse(LogMessages.VALIDATING_DOCUMENT_IN_DATABASE.isEmpty());
		assertFalse(LogMessages.CONSUMING_PATH_USERS.isEmpty());
		assertFalse(LogMessages.CONSUMING_PATH_LOGIN.isEmpty());
		assertFalse(LogMessages.CONSUMING_PATH_DOCUMENT.isEmpty());
	}
}
