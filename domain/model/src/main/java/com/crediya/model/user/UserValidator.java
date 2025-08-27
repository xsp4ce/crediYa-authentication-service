package com.crediya.model.user;

import com.crediya.model.user.exceptions.ValidationException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserValidator {
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
	private static final BigDecimal MIN_SALARY = BigDecimal.ZERO;
	private static final BigDecimal MAX_SALARY = new BigDecimal("15000000");

	public Mono<Void> validateUser(User user) {
		return Mono.fromRunnable(() -> {
			validateRequiredFields(user);
			validateBirthDate(user.getBirthDate());
			validateEmailFormat(user.getEmail());
			validateSalaryRange(user.getBaseSalary());
			validateDocumentNumber(user.getDocumentNumber());
		});
	}

	private void validateRequiredFields(User user) {
		if (isNullOrEmpty(user.getName())) {
			throw new ValidationException("Name is required.");
		}
		if (isNullOrEmpty(user.getLastName())) {
			throw new ValidationException("Last name is required.");
		}
		if (user.getBirthDate() == null) {
			throw new ValidationException("Birthdate is required.");
		}
		if (isNullOrEmpty(user.getAddress())) {
			throw new ValidationException("Address is required.");
		}
		if (isNullOrEmpty(user.getPhone())) {
			throw new ValidationException("Phone is required.");
		}
		if (isNullOrEmpty(user.getEmail())) {
			throw new ValidationException("Email is required.");
		}
		if (user.getBaseSalary() == null) {
			throw new ValidationException("Base salary is required.");
		}
		if (isNullOrEmpty(user.getDocumentNumber())) {
			throw new ValidationException("Document number is required.");
		}
	}

	private void validateEmailFormat(String email) {
		if (!email.matches(EMAIL_PATTERN)) {
			throw new ValidationException("Email format is invalid");
		}
	}

	private void validateSalaryRange(BigDecimal salary) {
		if (salary.compareTo(MIN_SALARY) <= 0 || salary.compareTo(MAX_SALARY) > 0) {
			throw new ValidationException("Salary must be between 0 and 15,000,000");
		}
	}

	private void validateDocumentNumber(String documentNumber) {
		if (documentNumber.length() != 8 || !documentNumber.matches("\\d+")) {
			throw new ValidationException("Document number must be numeric and up to 8 digits long");
		}
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	private void validateBirthDate(LocalDate birthDate) {
		if (birthDate.isAfter(LocalDate.now())) {
			throw new ValidationException("Birthdate cannot be in the future");
		}
		if (birthDate.isBefore(LocalDate.now().minusYears(120))) {
			throw new ValidationException("Birthdate cannot be more than 120 years ago");
		}
	}
}
