package com.crediya.model.login;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class LoginUserValidatorTest {

	private LoginUserValidator validator;
	private Login validLogin;

	@BeforeEach
	void setUp() {
		validator = new LoginUserValidator();
		validLogin = Login.builder().email("test@example.com").password("password123").build();
	}

	@Test
	void shouldValidateValidLogin() {
		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldFailWhenEmailIsNull() {
		validLogin.setEmail(null);

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenEmailIsEmpty() {
		validLogin.setEmail("");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenEmailIsBlank() {
		validLogin.setEmail("   ");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_REQUIRED)).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"invalid-email", "test@", "@domain.com", "test.com"})
	void shouldFailForInvalidEmailFormat(String invalidEmail) {
		validLogin.setEmail(invalidEmail);

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_INVALID_FORMAT)).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"test@domain.com", "user123@example.org", "test.email+tag@domain.co.uk"})
	void shouldPassForValidEmailFormats(String validEmail) {
		validLogin.setEmail(validEmail);

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldFailWhenPasswordIsNull() {
		validLogin.setPassword(null);

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.PASSWORD_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenPasswordIsEmpty() {
		validLogin.setPassword("");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.PASSWORD_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenPasswordIsTooShort() {
		validLogin.setPassword("1234567");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.PASSWORD_MIN_LENGTH)).verify();
	}

	@Test
	void shouldPassWhenPasswordIsExactlyEightCharacters() {
		validLogin.setPassword("12345678");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldPassWhenPasswordIsLongerThanMinimum() {
		validLogin.setPassword("verylongpassword123");

		Mono<Void> result = validator.validateLoginFields(validLogin);

		StepVerifier.create(result).verifyComplete();
	}
}
