package com.crediya.model.command;

import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import reactor.core.publisher.Mono;

public class CommandValidator {
	public Mono<ValidateDocumentCommand> validate(ValidateDocumentCommand cmd) {
		if (cmd.documentNumber() == null || cmd.documentNumber().isBlank()) {
			return Mono.error(new ValidationException(ValidationMessages.DOCUMENT_NUMBER_REQUIRED));
		}

		if (cmd.documentNumber().length() != 8) {
			return Mono.error(new ValidationException(ValidationMessages.DOCUMENT_MIN_LENGTH));
		}

		if (cmd.idUser() == null) {
			return Mono.error(new ValidationException(ValidationMessages.ID_USER_REQUIRED));
		}

		return Mono.just(cmd);
	}
}
