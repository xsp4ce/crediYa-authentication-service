package com.crediya.model.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateDocumentCommandTest {

	@Test
	void shouldCreateCommandWithDocumentNumberAndUserId() {
		String documentNumber = "12345678";
		Long userId = 1L;

		ValidateDocumentCommand command = new ValidateDocumentCommand(documentNumber, userId);

		assertEquals(documentNumber, command.documentNumber());
		assertEquals(userId, command.idUser());
	}

	@Test
	void shouldHandleNullValues() {
		ValidateDocumentCommand command = new ValidateDocumentCommand(null, null);

		assertNull(command.documentNumber());
		assertNull(command.idUser());
	}

	@Test
	void shouldBeEqualWhenSameValues() {
		ValidateDocumentCommand command1 = new ValidateDocumentCommand("12345678", 1L);
		ValidateDocumentCommand command2 = new ValidateDocumentCommand("12345678", 1L);

		assertEquals(command1, command2);
		assertEquals(command1.hashCode(), command2.hashCode());
	}

	@Test
	void shouldNotBeEqualWhenDifferentValues() {
		ValidateDocumentCommand command1 = new ValidateDocumentCommand("12345678", 1L);
		ValidateDocumentCommand command2 = new ValidateDocumentCommand("87654321", 2L);

		assertNotEquals(command1, command2);
	}

	@Test
	void shouldHaveProperToString() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("12345678", 1L);
		String toString = command.toString();

		assertTrue(toString.contains("12345678"));
		assertTrue(toString.contains("1"));
	}
}
