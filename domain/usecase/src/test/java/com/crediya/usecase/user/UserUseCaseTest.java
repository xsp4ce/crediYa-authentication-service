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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserValidator userValidator;

	@Mock
	private CommandValidator commandValidator;

	private UserUseCase userUseCase;
	private User validUser;
	private ValidateDocumentCommand validCommand;

	@BeforeEach
	void setUp() {
		userUseCase = new UserUseCase(userRepository, userValidator, commandValidator);

		validUser = User.builder().firstName("John").lastName("Doe").email("john.doe@example.com").password("password123")
		 .baseSalary(new BigDecimal("50000")).build();

		validCommand = new ValidateDocumentCommand("12345678", 1L);
	}

	@Test
	void shouldSaveUserSuccessfully() {
		User savedUser = validUser.toBuilder().id(1L).roleId(RoleEnum.CUSTOMER.getId()).build();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.empty());
		when(userRepository.save(any(User.class))).thenReturn(Mono.just(savedUser));

		Mono<User> result = userUseCase.save(validUser);

		StepVerifier.create(result).expectNext(savedUser).verifyComplete();

		verify(userValidator).validateUser(validUser);
		verify(userRepository).findByEmail(validUser.getEmail());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void shouldSetCustomerRoleWhenSavingUser() {
		User savedUser = validUser.toBuilder().id(1L).roleId(RoleEnum.CUSTOMER.getId()).build();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.empty());
		when(userRepository.save(any(User.class))).thenReturn(Mono.just(savedUser));

		Mono<User> result = userUseCase.save(validUser);

		StepVerifier.create(result).expectNext(savedUser).verifyComplete();

		verify(userRepository).save(argThat(user -> user.getRoleId().equals(RoleEnum.CUSTOMER.getId())));
	}

	@Test
	void shouldFailWhenEmailAlreadyExists() {
		User existingUser = User.builder().id(2L).email(validUser.getEmail()).build();

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.just(existingUser));

		Mono<User> result = userUseCase.save(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(ValidationMessages.EMAIL_ALREADY_EXISTS)).verify();

		verify(userValidator).validateUser(validUser);
		verify(userRepository).findByEmail(validUser.getEmail());
		verify(userRepository, never()).save(any());
	}

	@Test
	void shouldHandleRepositorySaveError() {
		RuntimeException repositoryError = new RuntimeException("Database connection failed");

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.empty());
		when(userRepository.save(any(User.class))).thenReturn(Mono.error(repositoryError));

		Mono<User> result = userUseCase.save(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals("Database connection failed")).verify();

		verify(userRepository).save(any(User.class));
	}

	@Test
	void shouldHandleUniqueConstraintValidationError() {
		ValidationException uniqueConstraintError = new ValidationException("Constraint violation");

		when(userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
		when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.error(uniqueConstraintError));

		Mono<User> result = userUseCase.save(validUser);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals("Constraint violation")).verify();

		verify(userRepository).findByEmail(validUser.getEmail());
		verify(userRepository, never()).save(any());
	}

	@Test
	void shouldValidateDocumentSuccessfullyWhenUserExists() {
		User foundUser = User.builder().id(1L).documentNumber("12345678").build();

		when(commandValidator.validate(validCommand)).thenReturn(Mono.just(validCommand));
		when(userRepository.findByDocumentNumber("12345678")).thenReturn(Mono.just(foundUser));

		Mono<Boolean> result = userUseCase.validateDocument(validCommand);

		StepVerifier.create(result).expectNext(true).verifyComplete();

		verify(commandValidator).validate(validCommand);
		verify(userRepository).findByDocumentNumber("12345678");
	}

	@Test
	void shouldReturnFalseWhenUserExistsButIdDoesNotMatch() {
		User foundUser = User.builder().id(2L) // Different ID
		 .documentNumber("12345678").build();

		when(commandValidator.validate(validCommand)).thenReturn(Mono.just(validCommand));
		when(userRepository.findByDocumentNumber("12345678")).thenReturn(Mono.just(foundUser));

		Mono<Boolean> result = userUseCase.validateDocument(validCommand);

		StepVerifier.create(result).expectNext(false).verifyComplete();

		verify(commandValidator).validate(validCommand);
		verify(userRepository).findByDocumentNumber("12345678");
	}

	@Test
	void shouldReturnFalseWhenUserNotFound() {
		when(commandValidator.validate(validCommand)).thenReturn(Mono.just(validCommand));
		when(userRepository.findByDocumentNumber("12345678")).thenReturn(Mono.empty());

		Mono<Boolean> result = userUseCase.validateDocument(validCommand);

		StepVerifier.create(result).expectNext(false).verifyComplete();

		verify(commandValidator).validate(validCommand);
		verify(userRepository).findByDocumentNumber("12345678");
	}

	@Test
	void shouldHandleRepositoryFindByDocumentError() {
		RuntimeException repositoryError = new RuntimeException("Database query failed");

		when(commandValidator.validate(validCommand)).thenReturn(Mono.just(validCommand));
		when(userRepository.findByDocumentNumber("12345678")).thenReturn(Mono.error(repositoryError));

		Mono<Boolean> result = userUseCase.validateDocument(validCommand);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals("Database query failed")).verify();

		verify(userRepository).findByDocumentNumber("12345678");
	}

	@Test
	void shouldValidateDocumentWithDifferentDocumentNumbers() {
		ValidateDocumentCommand command1 = new ValidateDocumentCommand("11111111", 1L);
		ValidateDocumentCommand command2 = new ValidateDocumentCommand("22222222", 2L);

		User user1 = User.builder().id(1L).documentNumber("11111111").build();
		User user2 = User.builder().id(2L).documentNumber("22222222").build();

		when(commandValidator.validate(command1)).thenReturn(Mono.just(command1));
		when(commandValidator.validate(command2)).thenReturn(Mono.just(command2));
		when(userRepository.findByDocumentNumber("11111111")).thenReturn(Mono.just(user1));
		when(userRepository.findByDocumentNumber("22222222")).thenReturn(Mono.just(user2));

		Mono<Boolean> result1 = userUseCase.validateDocument(command1);
		Mono<Boolean> result2 = userUseCase.validateDocument(command2);

		StepVerifier.create(result1).expectNext(true).verifyComplete();

		StepVerifier.create(result2).expectNext(true).verifyComplete();
	}

	@Test
	void shouldCreateUseCaseWithAllDependencies() {
		UserUseCase useCase = new UserUseCase(userRepository, userValidator, commandValidator);

		assertEquals(userRepository, useCase.userRepository());
		assertEquals(userValidator, useCase.userValidator());
		assertEquals(commandValidator, useCase.commandValidator());
	}

	@Test
	void shouldHandleNullDependencies() {
		UserUseCase useCase = new UserUseCase(null, null, null);

		assertNull(useCase.userRepository());
		assertNull(useCase.userValidator());
		assertNull(useCase.commandValidator());
	}
}
