package com.crediya.model.user.constants;

public class LogMessages {
	public static final String SAVING_USER_IN_DATABASE = "Saving user in the database";
	public static final String VALIDATING_EMAIL_IN_DATABASE = "Validating if email is already registered in the database";
	public static final String STARTING_TO_PROCESS_REQUEST = "Starting to process the request to save a user";
	public static final String CONSUMING_PATH_USERS = "Consuming path: /api/v1/users";

	LogMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
