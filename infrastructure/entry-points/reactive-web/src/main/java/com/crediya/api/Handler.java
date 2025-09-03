package com.crediya.api;

import com.crediya.api.dto.LoginUserDTO;
import com.crediya.api.dto.ResponseTokenDTO;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.dto.ValidateDocumentDTO;
import com.crediya.api.exception.CustomErrorResponse;
import com.crediya.api.mapper.IUserMapper;
import com.crediya.api.security.RequireRole;
import com.crediya.model.role.RoleConstants;
import com.crediya.model.user.constants.LogMessages;
import com.crediya.model.user.exceptions.ExceptionMessages;
import com.crediya.model.user.exceptions.ValidationException;
import com.crediya.usecase.login.LoginUserUseCase;
import com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class Handler {
	private final UserUseCase userUseCase;
	private final LoginUserUseCase loginUserUseCase;
	private final IUserMapper userMapper;

	@RequireRole({RoleConstants.ADMINISTRATOR, RoleConstants.ADVISOR})
	public Mono<ServerResponse> listenSaveUser(ServerRequest request) {
		log.info(LogMessages.CONSUMING_PATH_USERS);
		return withValidationErrorHandling(
		 request
			.bodyToMono(SaveUserDTO.class)
			.map(userMapper::toModel)
			.flatMap(userUseCase::save)
			.then(ServerResponse.status(HttpStatus.CREATED).build())
		);
	}

	public Mono<ServerResponse> listenValidateDocument(ServerRequest request) {
		log.info(LogMessages.CONSUMING_PATH_DOCUMENT);
		return request
		 .bodyToMono(ValidateDocumentDTO.class)
		 .map(userMapper::toCommand)
		 .flatMap(userUseCase::validateDocument)
		 .flatMap(isValid -> Boolean.TRUE.equals(isValid)
			? ServerResponse.ok().build()
			: ServerResponse.status(HttpStatus.CONFLICT).build()
		 );
	}

	public Mono<ServerResponse> listenLoginUser(ServerRequest request) {
		log.info(LogMessages.CONSUMING_PATH_LOGIN);
		return withValidationErrorHandling(
		 request
			.bodyToMono(LoginUserDTO.class)
			.map(userMapper::toLoginModel)
			.flatMap(loginUserUseCase::authenticate)
			.flatMap(token -> ServerResponse.ok()
			 .contentType(MediaType.APPLICATION_JSON)
			 .bodyValue(new ResponseTokenDTO(token))
			)
		);
	}

	private Mono<ServerResponse> withValidationErrorHandling(Mono<ServerResponse> responseMono) {
		return responseMono
		 .onErrorResume(ValidationException.class, ex -> ServerResponse.badRequest()
		 .contentType(MediaType.APPLICATION_JSON)
		 .bodyValue(new CustomErrorResponse(ExceptionMessages.VALIDATION_ERROR, ex.getMessage())));
	}
}