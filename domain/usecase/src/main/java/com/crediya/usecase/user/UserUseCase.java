package com.crediya.usecase.user;

import com.crediya.model.command.CommandValidator;
import com.crediya.model.command.ValidateDocumentCommand;
import com.crediya.model.role.RoleEnum;
import com.crediya.model.user.User;
import com.crediya.model.user.UserValidator;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.BusinessException;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record UserUseCase(UserRepository userRepository, UserValidator userValidator,
													CommandValidator commandValidator) {

	public Mono<User> save(User user) {
		return userValidator
		 .validateUser(user)
		 .then(validateUniqueConstraints(user))
		 .then(Mono.fromSupplier(() -> {
			 user.setRoleId(RoleEnum.CUSTOMER.getId());
			 return user;
		 }))
		 .flatMap(userRepository::save)
		 .onErrorMap(e -> new BusinessException(e.getMessage()));
	}

	public Mono<String> validateDocument(ValidateDocumentCommand cmd) {
		return commandValidator
		 .validate(cmd)
		 .then(userRepository.findByDocumentNumber(cmd.documentNumber()))
		 .filter(user -> user.getId().equals(cmd.idUser()))
		 .map(User::getEmail)
		 .switchIfEmpty(Mono.error(new BusinessException(ValidationMessages.DOCUMENT_VALIDATION_FAILED)))
		 .onErrorMap(e -> new BusinessException(e.getMessage()));
	}

	private Mono<Void> validateUniqueConstraints(User user) {
		return userRepository
		 .findByEmail(user.getEmail())
		 .flatMap(existingUser -> Mono.error(new ValidationException(ValidationMessages.EMAIL_ALREADY_EXISTS))).then()
		 .onErrorMap(e -> new BusinessException(e.getMessage()));
	}
}