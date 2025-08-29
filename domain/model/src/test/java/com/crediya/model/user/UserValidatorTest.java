package com.crediya.model.user;

import com.crediya.model.user.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

class UserValidatorTest {

	private UserValidator userValidator;

	@BeforeEach
	void setUp() {
		userValidator = new UserValidator();
	}

	@Test
	void shouldValidateValidUser() {
		User validUser =
		 User.builder().name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1)).address("123 Main St")
			.phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
			.documentNumber("12345678").build();

		Mono<Void> result = userValidator.validateUser(validUser);

		StepVerifier.create(result).verifyComplete();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "   "})
	void shouldFailWhenNameIsInvalid(String invalidName) {
		User user = createValidUserBuilder().name(invalidName).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenNameIsNull() {
		User user = createValidUserBuilder().name(null).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "   "})
	void shouldFailWhenLastNameIsInvalid(String invalidName) {
		User user = createValidUserBuilder().lastName(invalidName).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenLastNameIsNull() {
		User user = createValidUserBuilder().lastName(null).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenEmailIsNull() {
		User user = createValidUserBuilder().email(null).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenEmailFormatIsInvalid() {
		User user = createValidUserBuilder().email("invalid-email").build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldValidateValidEmailFormats() {
		String[] validEmails =
		 {"test@example.com", "user.name@domain.co.uk", "user+tag@example.org", "user_name@domain.info"};

		for (String email : validEmails) {
			User user = createValidUserBuilder().email(email).build();
			Mono<Void> result = userValidator.validateUser(user);
			StepVerifier.create(result).verifyComplete();
		}
	}

	@Test
	void shouldFailWhenBaseSalaryIsNull() {
		User user = createValidUserBuilder().baseSalary(null).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenBaseSalaryIsZero() {
		User user = createValidUserBuilder().baseSalary(BigDecimal.ZERO).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenBaseSalaryIsNegative() {
		User user = createValidUserBuilder().baseSalary(BigDecimal.valueOf(-1000)).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenBaseSalaryExceedsMaximum() {
		User user = createValidUserBuilder().baseSalary(new BigDecimal("15000001")).build();

		Mono<Void> result = userValidator.validateUser(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldValidateBoundaryValues() {
		User userWithMinSalary = createValidUserBuilder().baseSalary(BigDecimal.valueOf(1)).build();
		StepVerifier.create(userValidator.validateUser(userWithMinSalary)).verifyComplete();

		User userWithMaxSalary = createValidUserBuilder().baseSalary(new BigDecimal("15000000")).build();
		StepVerifier.create(userValidator.validateUser(userWithMaxSalary)).verifyComplete();
	}

	private User.UserBuilder createValidUserBuilder() {
		return User.builder().name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1)).address("123 Main St")
		 .phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
		 .documentNumber("12345678");
	}
}