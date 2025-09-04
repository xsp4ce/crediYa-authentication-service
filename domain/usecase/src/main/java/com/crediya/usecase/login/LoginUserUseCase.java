package com.crediya.usecase.login;

import com.crediya.model.login.Login;
import com.crediya.model.login.LoginUserValidator;
import com.crediya.model.login.Token;
import com.crediya.model.login.gateways.TokenRepository;
import com.crediya.model.user.User;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record LoginUserUseCase(UserRepository userRepository, LoginUserValidator loginUserValidator,
															 TokenRepository tokenRepository) {

	public Mono<Token> authenticate(Login login) {
		return loginUserValidator
		 .validateLoginFields(login)
		 .then(userRepository.findByEmail(login.getEmail())
			.switchIfEmpty(Mono.error(new ValidationException(ValidationMessages.USER_NOT_FOUND)))
		 )
		 .flatMap(user -> validateUserPassword(user, login.getPassword()))
		 .flatMap(tokenRepository::generateFor);
	}

	private Mono<User> validateUserPassword(User user, String rawPassword) {
		String raw = rawPassword != null ? rawPassword : "";
		String stored = user.getPassword() != null ? user.getPassword() : "";

		return raw.equals(stored) ? Mono.just(user) :
		 Mono.error(new ValidationException(ValidationMessages.USER_NOT_FOUND));
	}
}
