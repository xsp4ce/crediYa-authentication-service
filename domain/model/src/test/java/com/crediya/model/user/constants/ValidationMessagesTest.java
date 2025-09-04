package com.crediya.model.user.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationMessagesTest {

	@Test
	void shouldHaveExpectedEmailMessages() {
		assertEquals("Email is already registered", ValidationMessages.EMAIL_ALREADY_REGISTERED);
		assertEquals("Email format is invalid", ValidationMessages.EMAIL_FORMAT_INVALID);
		assertEquals("Email cannot be null", ValidationMessages.EMAIL_CANNOT_BE_NULL);
		assertEquals("Email is required", ValidationMessages.EMAIL_REQUIRED);
		assertEquals("Invalid email format", ValidationMessages.EMAIL_INVALID_FORMAT);
		assertEquals("Email already exists", ValidationMessages.EMAIL_ALREADY_EXISTS);
	}

	@Test
	void shouldHaveExpectedUserMessages() {
		assertEquals("User cannot be null", ValidationMessages.USER_CANNOT_BE_NULL);
		assertEquals("User not found", ValidationMessages.USER_NOT_FOUND);
		assertEquals("Name is required", ValidationMessages.NAME_REQUIRED);
		assertEquals("Name must not exceed 50 characters", ValidationMessages.NAME_MAX_LENGTH);
		assertEquals("Last name is required", ValidationMessages.LAST_NAME_REQUIRED);
		assertEquals("Last name must not exceed 50 characters", ValidationMessages.LAST_NAME_MAX_LENGTH);
	}

	@Test
	void shouldHaveExpectedPasswordMessages() {
		assertEquals("Password is required", ValidationMessages.PASSWORD_REQUIRED);
		assertEquals("Password must be at least 8 characters long", ValidationMessages.PASSWORD_MIN_LENGTH);
	}

	@Test
	void shouldHaveExpectedSalaryMessages() {
		assertEquals("Salary must be between 0 and 15,000,000", ValidationMessages.SALARY_OUT_OF_RANGE);
		assertEquals("Base salary is required", ValidationMessages.BASE_SALARY_REQUIRED);
		assertEquals("Base salary must be greater than 0", ValidationMessages.BASE_SALARY_MUST_BE_POSITIVE);
	}

	@Test
	void shouldHaveExpectedDocumentMessages() {
		assertEquals("Document number is required", ValidationMessages.DOCUMENT_NUMBER_REQUIRED);
		assertEquals("Document must be 8 characters", ValidationMessages.DOCUMENT_MIN_LENGTH);
		assertEquals("User ID is required", ValidationMessages.ID_USER_REQUIRED);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, ValidationMessages::new);
	}
}
