package com.crediya.model.user.constants;

public class LogMessages {
	public static final String SAVING_USER_IN_DATABASE = "Saving user in the database";
	public static final String VALIDATING_EMAIL_IN_DATABASE = "Validating if email is already registered in the database";
	public static final String VALIDATING_DOCUMENT_IN_DATABASE = "Validating if document number is already registered in the database";
	public static final String CONSUMING_PATH_USERS = "Consuming path: /api/v1/users";
	public static final String CONSUMING_PATH_LOGIN = "Consuming path: /api/v1/login";
	public static final String CONSUMING_PATH_DOCUMENT = "Consuming path: /api/v1/document";

	LogMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
