package com.crediya.usecase.user;

import com.crediya.model.user.User;
import com.crediya.model.user.UserValidator;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record UserUseCase(UserRepository userRepository, UserValidator userValidator) {
	public Mono<User> save(User user) {
		return userValidator.validateUser(user).then(Mono.defer(() -> validateUniqueConstraints(user)))
		 .then(Mono.defer(() -> userRepository.save(user)));
	}

	private Mono<Void> validateUniqueConstraints(User user) {
		return Mono.defer(() -> Mono.when(userRepository.existsByEmail(user.getEmail())
		 .flatMap(exists -> Boolean.TRUE.equals(exists) ?
			Mono.error(new ValidationException("Email is already registered")) :
			Mono.empty()), userRepository.existsByDocumentNumber(user.getDocumentNumber())
		 .flatMap(exists -> Boolean.TRUE.equals(exists) ?
			Mono.error(new ValidationException("Document number is already registered")) : Mono.empty())));
	}
}
