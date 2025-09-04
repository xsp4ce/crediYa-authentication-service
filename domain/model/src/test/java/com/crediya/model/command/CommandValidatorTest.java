package com.crediya.model.command;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CommandValidatorTest {

	private CommandValidator validator;
	private ValidateDocumentCommand validCommand;

	@BeforeEach
	void setUp() {
		validator = new CommandValidator();
		validCommand = new ValidateDocumentCommand("12345678", 1L);
	}

	@Test
	void shouldValidateValidCommand() {
		Mono<ValidateDocumentCommand> result = validator.validate(validCommand);

		StepVerifier.create(result).expectNext(validCommand).verifyComplete();
	}

	@Test
	void shouldFailWhenDocumentNumberIsNull() {
		ValidateDocumentCommand command = new ValidateDocumentCommand(null, 1L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.DOCUMENT_NUMBER_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsEmpty() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("", 1L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.DOCUMENT_NUMBER_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsBlank() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("   ", 1L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.DOCUMENT_NUMBER_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsNotEightCharacters() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("1234567", 1L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.DOCUMENT_MIN_LENGTH)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsTooLong() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("123456789", 1L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.DOCUMENT_MIN_LENGTH)).verify();
	}

	@Test
	void shouldFailWhenUserIdIsNull() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("12345678", null);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.ID_USER_REQUIRED)).verify();
	}

	@Test
	void shouldPassWithValidEightDigitDocument() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("87654321", 2L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectNext(command).verifyComplete();
	}

	@Test
	void shouldPassWithAlphanumericDocument() {
		ValidateDocumentCommand command = new ValidateDocumentCommand("AB123456", 3L);

		Mono<ValidateDocumentCommand> result = validator.validate(command);

		StepVerifier.create(result).expectNext(command).verifyComplete();
	}
}
