package com.crediya.usecase.login;

import com.crediya.model.login.Login;
import com.crediya.model.login.LoginUserValidator;
import com.crediya.model.login.Token;
import com.crediya.model.login.gateways.TokenRepository;
import com.crediya.model.user.User;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private LoginUserValidator loginUserValidator;

	@Mock
	private TokenRepository tokenRepository;

	private LoginUserUseCase loginUserUseCase;
	private Login validLogin;
	private User validUser;
	private Token validToken;

	@BeforeEach
	void setUp() {
		loginUserUseCase = new LoginUserUseCase(userRepository, loginUserValidator, tokenRepository);

		validLogin = Login.builder().email("john.doe@example.com").password("password123").build();

		validUser =
		 User.builder().id(1L).email("john.doe@example.com").password("password123").firstName("John").lastName("Doe")
			.build();

		validToken = new Token("jwt-token-value", Instant.now().plusSeconds(3600));
	}

	@Test
	void shouldAuthenticateUserSuccessfully() {
		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.just(validUser));
		when(tokenRepository.generateFor(validUser)).thenReturn(Mono.just(validToken));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectNext(validToken).verifyComplete();

		verify(loginUserValidator).validateLoginFields(validLogin);
		verify(userRepository).findByEmail(validLogin.getEmail());
		verify(tokenRepository).generateFor(validUser);
	}

	@Test
	void shouldAuthenticateWithExactPasswordMatch() {
		Login login = Login.builder().email("test@example.com").password("exact-password").build();

		User user = validUser.toBuilder().email("test@example.com").password("exact-password").build();

		when(loginUserValidator.validateLoginFields(login)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(login.getEmail())).thenReturn(Mono.just(user));
		when(tokenRepository.generateFor(user)).thenReturn(Mono.just(validToken));

		Mono<Token> result = loginUserUseCase.authenticate(login);

		StepVerifier.create(result).expectNext(validToken).verifyComplete();
	}

	@Test
	void shouldFailWhenUserNotFound() {
		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.empty());

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.USER_NOT_FOUND)).verify();

		verify(loginUserValidator).validateLoginFields(validLogin);
		verify(userRepository).findByEmail(validLogin.getEmail());
		verify(tokenRepository, never()).generateFor(any());
	}

	@Test
	void shouldFailWhenRepositoryFindByEmailFails() {
		RuntimeException repositoryError = new RuntimeException("Database connection failed");

		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.error(repositoryError));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();

		verify(userRepository).findByEmail(validLogin.getEmail());
		verify(tokenRepository, never()).generateFor(any());
	}

	@Test
	void shouldFailWhenPasswordDoesNotMatch() {
		User userWithDifferentPassword = validUser.toBuilder().password("different-password").build();

		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.just(userWithDifferentPassword));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.USER_NOT_FOUND)).verify();

		verify(loginUserValidator).validateLoginFields(validLogin);
		verify(userRepository).findByEmail(validLogin.getEmail());
		verify(tokenRepository, never()).generateFor(any());
	}

	@Test
	void shouldHandleNullPasswordFromLogin() {
		Login loginWithNullPassword = validLogin.toBuilder().password(null).build();

		User userWithValidPassword = validUser.toBuilder().password("password123").build();

		when(loginUserValidator.validateLoginFields(loginWithNullPassword)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(loginWithNullPassword.getEmail())).thenReturn(Mono.just(userWithValidPassword));

		Mono<Token> result = loginUserUseCase.authenticate(loginWithNullPassword);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.USER_NOT_FOUND)).verify();
	}

	@Test
	void shouldHandleNullPasswordFromUser() {
		User userWithNullPassword = validUser.toBuilder().password(null).build();

		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.just(userWithNullPassword));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.USER_NOT_FOUND)).verify();
	}

	@Test
	void shouldHandleBothPasswordsNull() {
		Login loginWithNullPassword = validLogin.toBuilder().password(null).build();

		User userWithNullPassword = validUser.toBuilder().password(null).build();

		when(loginUserValidator.validateLoginFields(loginWithNullPassword)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(loginWithNullPassword.getEmail())).thenReturn(Mono.just(userWithNullPassword));
		when(tokenRepository.generateFor(userWithNullPassword)).thenReturn(Mono.just(validToken));

		Mono<Token> result = loginUserUseCase.authenticate(loginWithNullPassword);

		StepVerifier.create(result).expectNext(validToken).verifyComplete();

		verify(tokenRepository).generateFor(userWithNullPassword);
	}

	@Test
	void shouldHandleBothPasswordsEmpty() {
		Login loginWithEmptyPassword = validLogin.toBuilder().password("").build();

		User userWithEmptyPassword = validUser.toBuilder().password("").build();

		when(loginUserValidator.validateLoginFields(loginWithEmptyPassword)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(loginWithEmptyPassword.getEmail())).thenReturn(Mono.just(userWithEmptyPassword));
		when(tokenRepository.generateFor(userWithEmptyPassword)).thenReturn(Mono.just(validToken));

		Mono<Token> result = loginUserUseCase.authenticate(loginWithEmptyPassword);

		StepVerifier.create(result).expectNext(validToken).verifyComplete();
	}

	@Test
	void shouldFailWhenTokenGenerationFails() {
		RuntimeException tokenError = new RuntimeException("Token generation failed");

		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.just(validUser));
		when(tokenRepository.generateFor(validUser)).thenReturn(Mono.error(tokenError));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();

		verify(tokenRepository).generateFor(validUser);
	}

	@Test
	void shouldGenerateTokenForValidUser() {
		Token expectedToken = new Token("custom-token", Instant.now().plusSeconds(7200));

		when(loginUserValidator.validateLoginFields(validLogin)).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validLogin.getEmail())).thenReturn(Mono.just(validUser));
		when(tokenRepository.generateFor(validUser)).thenReturn(Mono.just(expectedToken));

		Mono<Token> result = loginUserUseCase.authenticate(validLogin);

		StepVerifier.create(result).expectNext(expectedToken).verifyComplete();

		verify(tokenRepository).generateFor(validUser);
	}

	@Test
	void shouldCreateUseCaseWithAllDependencies() {
		LoginUserUseCase useCase = new LoginUserUseCase(userRepository, loginUserValidator, tokenRepository);

		assertEquals(userRepository, useCase.userRepository());
		assertEquals(loginUserValidator, useCase.loginUserValidator());
		assertEquals(tokenRepository, useCase.tokenRepository());
	}

	@Test
	void shouldHandleNullDependencies() {
		LoginUserUseCase useCase = new LoginUserUseCase(null, null, null);

		assertNull(useCase.userRepository());
		assertNull(useCase.loginUserValidator());
		assertNull(useCase.tokenRepository());
	}
}
