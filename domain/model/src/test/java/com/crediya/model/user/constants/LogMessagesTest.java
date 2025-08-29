package com.crediya.model.user.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessagesTest {

	@Test
	void shouldNotBeInstantiable() {
		assertThrows(UnsupportedOperationException.class, LogMessages::new);
	}

	@Test
	void shouldHaveExpectedMessages() {
		assertEquals("Saving user in the database", LogMessages.SAVING_USER_IN_DATABASE);
		assertEquals("Validating if email is already registered in the database", LogMessages.VALIDATING_EMAIL_IN_DATABASE);
		assertEquals("Starting to process the request to save a user", LogMessages.STARTING_TO_PROCESS_REQUEST);
		assertEquals("Consuming path: /api/v1/users", LogMessages.CONSUMING_PATH_USERS);
	}
}
