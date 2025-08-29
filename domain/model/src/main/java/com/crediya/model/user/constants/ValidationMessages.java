package com.crediya.model.user.constants;

public final class ValidationMessages {
	public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered";
	public static final String EMAIL_FORMAT_INVALID = "Email format is invalid";
	public static final String EMAIL_CANNOT_BE_NULL = "Email cannot be null";
	public static final String SALARY_OUT_OF_RANGE = "Salary must be between 0 and 15,000,000";
	public static final String USER_CANNOT_BE_NULL = "User cannot be null";

	public static final String NAME_REQUIRED = "Name is required";
	public static final String NAME_MAX_LENGTH = "Name must not exceed 50 characters";
	public static final String LAST_NAME_REQUIRED = "Last name is required";
	public static final String LAST_NAME_MAX_LENGTH = "Last name must not exceed 50 characters";
	public static final String EMAIL_REQUIRED = "Email is required";
	public static final String EMAIL_INVALID_FORMAT = "Invalid email format";
	public static final String BASE_SALARY_REQUIRED = "Base salary is required";
	public static final String BASE_SALARY_MUST_BE_POSITIVE = "Base salary must be greater than 0";

	ValidationMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
