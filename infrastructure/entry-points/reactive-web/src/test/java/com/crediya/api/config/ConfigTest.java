package com.crediya.api.config;

import com.crediya.api.Handler;
import com.crediya.api.RouterRest;
import com.crediya.api.constants.UserPaths;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.mapper.IUserMapper;
import com.crediya.model.user.User;
import com.crediya.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private UserUseCase userUseCase;

	@MockitoBean
	private IUserMapper userMapper;

	private final User user = User.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe").build();

	private final SaveUserDTO saveUserDTO =
	 new SaveUserDTO("12345678", "John", "Doe", LocalDate.of(1990, 5, 15), "Calle 123 #45-67, Bogotá", "3001234567",
		"juan" + ".perez7@email.com", BigDecimal.valueOf(5000000.00));

	@BeforeEach
	void setUp() {
		when(userUseCase.save(user)).thenReturn(Mono.just(user));
		when(userMapper.toModel(saveUserDTO)).thenReturn(user);
	}

	@Test
	void corsConfigurationShouldAllowOrigins() {
		webTestClient.post().uri(UserPaths.USERS).contentType(MediaType.APPLICATION_JSON).bodyValue(saveUserDTO).exchange()
		 .expectStatus().isCreated().expectHeader()
		 .valueEquals("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
		 .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;").expectHeader()
		 .valueEquals("X-Content-Type-Options", "nosniff").expectHeader().valueEquals("Server", "").expectHeader()
		 .valueEquals("Cache-Control", "no-store").expectHeader().valueEquals("Pragma", "no-cache").expectHeader()
		 .valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
	}
}