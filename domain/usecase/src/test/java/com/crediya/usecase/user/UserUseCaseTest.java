package com.crediya.usecase.user;

import com.crediya.model.user.User;
import com.crediya.model.user.UserValidator;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ExceptionMessages;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserValidator userValidator;

	@InjectMocks
	private UserUseCase userUseCase;

	@Test
	void shouldSaveUserSuccessfully() {
		User user = createValidUser();
		User savedUser = user.toBuilder().id(1L).build();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
		when(userRepository.save(any(User.class))).thenReturn(Mono.just(savedUser));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectNext(savedUser).verifyComplete();
	}

	@Test
	void shouldFailWhenValidationFails() {
		User user = createValidUser();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.error(new ValidationException(ValidationMessages.NAME_REQUIRED)));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectError(ValidationException.class).verify();
	}

	@Test
	void shouldFailWhenEmailAlreadyExists() {
		User user = createValidUser();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof ValidationException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_ALREADY_REGISTERED)).verify();
	}

	@Test
	void shouldFailWhenRepositorySaveFails() {
		User user = createValidUser();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
		when(userRepository.save(any(User.class))).thenReturn(Mono.error(new RuntimeException(ExceptionMessages.DATABASE_ERROR)));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	@Test
	void shouldHandleRepositoryExistsMethodErrors() {
		User user = createValidUser();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.existsByEmail(anyString())).thenReturn(Mono.error(new RuntimeException(ExceptionMessages.DATABASE_ERROR)));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	@Test
	void shouldContinueWhenEmailCheckPassesButDocumentCheckFails() {
		User user = createValidUser();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));

		Mono<User> result = userUseCase.save(user);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	private User createValidUser() {
		return User.builder().documentNumber("12345678").name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
		 .address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
		 .build();
	}
}
