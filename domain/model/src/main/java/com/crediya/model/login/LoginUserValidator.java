package com.crediya.model.login;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import reactor.core.publisher.Mono;

public class LoginUserValidator {
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
	private static final int MIN_PASSWORD_LENGTH = 8;

	public Mono<Void> validateLoginFields(Login login) {
		return Mono.fromRunnable(() -> {
			validateEmail(login.getEmail());
			validatePassword(login.getPassword());
		});
	}

	private void validateEmail(String email) {
		if (isNullOrEmpty(email)) {
			throw new ValidationException(ValidationMessages.EMAIL_REQUIRED);
		}
		if (!email.matches(EMAIL_PATTERN)) {
			throw new ValidationException(ValidationMessages.EMAIL_INVALID_FORMAT);
		}
	}

	private void validatePassword(String password) {
		if (isNullOrEmpty(password)) {
			throw new ValidationException(ValidationMessages.PASSWORD_REQUIRED);
		}
		if (password.length() < MIN_PASSWORD_LENGTH) {
			throw new ValidationException(ValidationMessages.PASSWORD_MIN_LENGTH);
		}
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
