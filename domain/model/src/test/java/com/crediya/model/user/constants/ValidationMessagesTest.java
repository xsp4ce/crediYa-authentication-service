package com.crediya.model.user.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationMessagesTest {

	@Test
	void shouldHaveNonEmptyErrorMessages() {
		assertFalse(ValidationMessages.EMAIL_ALREADY_REGISTERED.isEmpty());
		assertFalse(ValidationMessages.EMAIL_FORMAT_INVALID.isEmpty());
		assertFalse(ValidationMessages.EMAIL_CANNOT_BE_NULL.isEmpty());
		assertFalse(ValidationMessages.SALARY_OUT_OF_RANGE.isEmpty());
		assertFalse(ValidationMessages.USER_CANNOT_BE_NULL.isEmpty());
	}

	@Test
	void shouldHaveNonEmptyValidationMessages() {
		assertFalse(ValidationMessages.NAME_REQUIRED.isEmpty());
		assertFalse(ValidationMessages.NAME_MAX_LENGTH.isEmpty());
		assertFalse(ValidationMessages.LAST_NAME_REQUIRED.isEmpty());
		assertFalse(ValidationMessages.LAST_NAME_MAX_LENGTH.isEmpty());
		assertFalse(ValidationMessages.EMAIL_REQUIRED.isEmpty());
		assertFalse(ValidationMessages.EMAIL_INVALID_FORMAT.isEmpty());
		assertFalse(ValidationMessages.BASE_SALARY_REQUIRED.isEmpty());
		assertFalse(ValidationMessages.BASE_SALARY_MUST_BE_POSITIVE.isEmpty());
	}

	@Test
	void shouldNotBeInstantiable() {
		assertThrows(UnsupportedOperationException.class, ValidationMessages::new);
	}

	@Test
	void shouldHaveExpectedMessages() {
		assertEquals("Email is already registered", ValidationMessages.EMAIL_ALREADY_REGISTERED);
		assertEquals("Email format is invalid", ValidationMessages.EMAIL_FORMAT_INVALID);
		assertEquals("Email cannot be null", ValidationMessages.EMAIL_CANNOT_BE_NULL);
		assertEquals("Salary must be between 0 and 15,000,000", ValidationMessages.SALARY_OUT_OF_RANGE);
		assertEquals("User cannot be null", ValidationMessages.USER_CANNOT_BE_NULL);
		assertEquals("Name is required", ValidationMessages.NAME_REQUIRED);
		assertEquals("Name must not exceed 50 characters", ValidationMessages.NAME_MAX_LENGTH);
		assertEquals("Last name is required", ValidationMessages.LAST_NAME_REQUIRED);
		assertEquals("Last name must not exceed 50 characters", ValidationMessages.LAST_NAME_MAX_LENGTH);
		assertEquals("Email is required", ValidationMessages.EMAIL_REQUIRED);
		assertEquals("Invalid email format", ValidationMessages.EMAIL_INVALID_FORMAT);
		assertEquals("Base salary is required", ValidationMessages.BASE_SALARY_REQUIRED);
		assertEquals("Base salary must be greater than 0", ValidationMessages.BASE_SALARY_MUST_BE_POSITIVE);
	}
}
