package com.crediya.model.user;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class UserValidatorTest {

	private UserValidator validator;
	private User validUser;

	@BeforeEach
	void setUp() {
		validator = new UserValidator();
		validUser = User.builder().firstName("John").lastName("Doe").email("john.doe@example.com").password("password123")
		 .baseSalary(new BigDecimal("50000")).build();
	}

	@Test
	void shouldValidateValidUser() {
		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldFailWhenFirstNameIsNull() {
		validUser.setFirstName(null);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.NAME_REQUIRED)).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "   "})
	void shouldFailWhenFirstNameIsInvalid(String invalidFirstName) {
		validUser.setFirstName(invalidFirstName);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.NAME_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenLastNameIsNull() {
		validUser.setLastName(null);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.LAST_NAME_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenEmailIsNull() {
		validUser.setEmail(null);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_REQUIRED)).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"invalid-email", "test", "domain.com", "test.com"})
	void shouldFailForInvalidEmailFormat(String invalidEmail) {
		validUser.setEmail(invalidEmail);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_FORMAT_INVALID)).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"test@domain.com", "user123@example.org", "test.email+tag@domain.co.uk"})
	void shouldPassForValidEmailFormats(String validEmail) {
		validUser.setEmail(validEmail);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldFailWhenPasswordIsNull() {
		validUser.setPassword(null);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.PASSWORD_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenBaseSalaryIsNull() {
		validUser.setBaseSalary(null);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.BASE_SALARY_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenSalaryIsZero() {
		validUser.setBaseSalary(BigDecimal.ZERO);

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.SALARY_OUT_OF_RANGE)).verify();
	}

	@Test
	void shouldFailWhenSalaryIsNegative() {
		validUser.setBaseSalary(new BigDecimal("-1000"));

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.SALARY_OUT_OF_RANGE)).verify();
	}

	@Test
	void shouldFailWhenSalaryExceedsMaximum() {
		validUser.setBaseSalary(new BigDecimal("15000001"));

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.SALARY_OUT_OF_RANGE)).verify();
	}

	@Test
	void shouldPassWhenSalaryIsAtMaximumLimit() {
		validUser.setBaseSalary(new BigDecimal("15000000"));

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldPassWhenSalaryIsMinimumValid() {
		validUser.setBaseSalary(new BigDecimal("0.01"));

		Mono<Void> result = validator.validateUser(validUser);

		StepVerifier.create(result).verifyComplete();
	}
}
