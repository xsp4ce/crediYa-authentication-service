package com.crediya.model.user.exceptions;

public class ExceptionMessages {

	public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
	public static final String UNEXPECTED_ERROR = "Unexpected error";
	public static final String DATABASE_ERROR = "Database error";
	public static final String FIELD_ALREADY_REGISTERED = "Field is already registered";

	ExceptionMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
