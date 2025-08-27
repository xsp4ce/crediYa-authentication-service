package com.crediya.r2dbc;

import com.crediya.model.user.User;
import com.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

	@InjectMocks
	private UserRepositoryAdapter repositoryAdapter;

	@Mock
	private IUserRepository repository;

	@Mock
	private ObjectMapper mapper;

	@Test
	void shouldSaveUserSuccessfully() {
		User user = createUser();
		UserEntity userEntity = createUserEntity();

		when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
		when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
		when(mapper.map(userEntity, User.class)).thenReturn(user);

		Mono<User> result = repositoryAdapter.save(user);

		StepVerifier.create(result).expectNext(user).verifyComplete();

		verify(mapper).map(user, UserEntity.class);
		verify(repository).save(userEntity);
		verify(mapper).map(userEntity, User.class);
	}

	@Test
	void shouldReturnErrorWhenRepositorySaveFails() {
		User user = createUser();
		UserEntity userEntity = createUserEntity();

		when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
		when(repository.save(userEntity)).thenReturn(Mono.error(new RuntimeException("Database error")));

		Mono<User> result = repositoryAdapter.save(user);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();

		verify(mapper).map(user, UserEntity.class);
		verify(repository).save(userEntity);
		verify(mapper, times(0)).map(any(UserEntity.class), eq(User.class));
	}

	@Test
	void shouldCheckIfEmailExists() {
		String email = "test@example.com";
		when(repository.existsByEmail(email)).thenReturn(Mono.just(true));

		Mono<Boolean> result = repositoryAdapter.existsByEmail(email);

		StepVerifier.create(result).expectNext(true).verifyComplete();

		verify(repository).existsByEmail(email);
	}

	@Test
	void shouldCheckIfEmailDoesNotExist() {
		String email = "nonexistent@example.com";
		when(repository.existsByEmail(email)).thenReturn(Mono.just(false));

		Mono<Boolean> result = repositoryAdapter.existsByEmail(email);

		StepVerifier.create(result).expectNext(false).verifyComplete();

		verify(repository).existsByEmail(email);
	}

	@Test
	void shouldReturnErrorWhenExistsByEmailFails() {
		String email = "test@example.com";
		when(repository.existsByEmail(email)).thenReturn(Mono.error(new RuntimeException("Database connection error")));

		Mono<Boolean> result = repositoryAdapter.existsByEmail(email);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	@Test
	void shouldCheckIfDocumentNumberExists() {
		String documentNumber = "12345678";
		when(repository.existsByDocumentNumber(documentNumber)).thenReturn(Mono.just(true));

		Mono<Boolean> result = repositoryAdapter.existsByDocumentNumber(documentNumber);

		StepVerifier.create(result).expectNext(true).verifyComplete();

		verify(repository).existsByDocumentNumber(documentNumber);
	}

	@Test
	void shouldCheckIfDocumentNumberDoesNotExist() {
		String documentNumber = "87654321";
		when(repository.existsByDocumentNumber(documentNumber)).thenReturn(Mono.just(false));

		Mono<Boolean> result = repositoryAdapter.existsByDocumentNumber(documentNumber);

		StepVerifier.create(result).expectNext(false).verifyComplete();

		verify(repository).existsByDocumentNumber(documentNumber);
	}

	@Test
	void shouldReturnErrorWhenExistsByDocumentNumberFails() {
		String documentNumber = "12345678";
		when(repository.existsByDocumentNumber(documentNumber)).thenReturn(Mono.error(new RuntimeException(
		 "Database " + "connection error")));

		Mono<Boolean> result = repositoryAdapter.existsByDocumentNumber(documentNumber);

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	@Test
	void shouldHandleNullEmailInExistsCheck() {
		when(repository.existsByEmail(null)).thenReturn(Mono.error(new IllegalArgumentException("Email cannot be null")));

		Mono<Boolean> result = repositoryAdapter.existsByEmail(null);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	void shouldHandleEmptyEmailInExistsCheck() {
		when(repository.existsByEmail("")).thenReturn(Mono.just(false));

		Mono<Boolean> result = repositoryAdapter.existsByEmail("");

		StepVerifier.create(result).expectNext(false).verifyComplete();
	}

	private User createUser() {
		return User.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe")
		 .birthDate(LocalDate.of(1990, 1, 1)).address("123 Main St").phone("1234567890").email("john.doe@example.com")
		 .baseSalary(BigDecimal.valueOf(5000000)).build();
	}

	private UserEntity createUserEntity() {
		return UserEntity.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe")
		 .birthDate(LocalDate.of(1990, 1, 1)).address("123 Main St").phone("1234567890").email("john.doe@example.com")
		 .baseSalary(BigDecimal.valueOf(5000000)).build();
	}
}