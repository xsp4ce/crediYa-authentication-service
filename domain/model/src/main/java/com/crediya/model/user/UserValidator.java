package com.crediya.model.user;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class UserValidator {
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
	private static final BigDecimal MIN_SALARY = BigDecimal.ZERO;
	private static final BigDecimal MAX_SALARY = new BigDecimal("15000000");

	public Mono<Void> validateUser(User user) {
		return Mono.fromRunnable(() -> {
			validateRequiredFields(user);
			validateEmailFormat(user.getEmail());
			validateSalaryRange(user.getBaseSalary());
		});
	}

	private void validateRequiredFields(User user) {
		if (isNullOrEmpty(user.getName())) {
			throw new ValidationException(ValidationMessages.NAME_REQUIRED);
		}
		if (isNullOrEmpty(user.getLastName())) {
			throw new ValidationException(ValidationMessages.LAST_NAME_REQUIRED);
		}
		if (isNullOrEmpty(user.getEmail())) {
			throw new ValidationException(ValidationMessages.EMAIL_REQUIRED);
		}
		if (user.getBaseSalary() == null) {
			throw new ValidationException(ValidationMessages.BASE_SALARY_REQUIRED);
		}
	}

	private void validateEmailFormat(String email) {
		if (!email.matches(EMAIL_PATTERN)) {
			throw new ValidationException(ValidationMessages.EMAIL_FORMAT_INVALID);
		}
	}

	private void validateSalaryRange(BigDecimal salary) {
		if (salary.compareTo(MIN_SALARY) <= 0 || salary.compareTo(MAX_SALARY) > 0) {
			throw new ValidationException(ValidationMessages.SALARY_OUT_OF_RANGE);
		}
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
