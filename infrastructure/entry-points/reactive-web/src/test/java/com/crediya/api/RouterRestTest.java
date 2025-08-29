package com.crediya.api;

import com.crediya.api.constants.UserPaths;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.mapper.IUserMapper;
import com.crediya.model.user.User;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ExceptionMessages;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.usecase.user.UserUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private UserUseCase userUseCase;

	@MockitoBean
	private IUserMapper userMapper;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Test
	void shouldCreateUserSuccessfully() throws JsonProcessingException {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890",
			"john" + ".doe@example.com", BigDecimal.valueOf(5000000));

		User user =
		 User.builder().documentNumber("12345678").name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
			.address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
			.build();

		User savedUser = user.toBuilder().id(1L).build();

		when(userMapper.toModel(any(SaveUserDTO.class))).thenReturn(user);
		when(userUseCase.save(any(User.class))).thenReturn(Mono.just(savedUser));

		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON)
		 .bodyValue(objectMapper.writeValueAsString(saveUserDTO)).exchange().expectStatus().isCreated();
	}

	@Test
	void shouldReturnBadRequestWhenValidationFails() throws JsonProcessingException {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890",
			"john.doe@example" + ".com", BigDecimal.valueOf(5000000));

		User user = User.builder().documentNumber("12345678").name("").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
		 .address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
		 .build();

		when(userMapper.toModel(any(SaveUserDTO.class))).thenReturn(user);
		when(userUseCase.save(any(User.class))).thenReturn(Mono.error(new ValidationException(ValidationMessages.NAME_REQUIRED)));

		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON)
		 .bodyValue(objectMapper.writeValueAsString(saveUserDTO)).exchange().expectStatus().isBadRequest();
	}

	@Test
	void shouldReturnBadRequestWhenEmailAlreadyExists() throws JsonProcessingException {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890",
			"existing" + "@example.com", BigDecimal.valueOf(5000000));

		User user =
		 User.builder().documentNumber("12345678").name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
			.address("123 Main St").phone("1234567890").email("existing@example.com").baseSalary(BigDecimal.valueOf(5000000))
			.build();

		when(userMapper.toModel(any(SaveUserDTO.class))).thenReturn(user);
		when(userUseCase.save(any(User.class))).thenReturn(Mono.error(new ValidationException(ValidationMessages.EMAIL_ALREADY_REGISTERED)));

		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON)
		 .bodyValue(objectMapper.writeValueAsString(saveUserDTO)).exchange().expectStatus().isBadRequest();
	}

	@Test
	void shouldReturnBadRequestForInvalidJsonBody() {
		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON).bodyValue("{invalid json}")
		 .exchange().expectStatus().isBadRequest();
	}

	@Test
	void shouldReturnBadRequestForEmptyBody() {
		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON).bodyValue("").exchange()
		 .expectStatus().isBadRequest();
	}

	@Test
	void shouldReturnInternalServerErrorForUnexpectedErrors() throws JsonProcessingException {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "1234567890",
			"john" + ".doe@example.com", BigDecimal.valueOf(5000000));

		when(userMapper.toModel(any(SaveUserDTO.class))).thenThrow(new RuntimeException(ExceptionMessages.UNEXPECTED_ERROR));

		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON)
		 .bodyValue(objectMapper.writeValueAsString(saveUserDTO)).exchange().expectStatus().is5xxServerError();
	}
}
