package com.crediya.api;

import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.exception.CustomErrorResponse;
import com.crediya.api.mapper.IUserMapper;
import com.crediya.model.user.constants.LogMessages;
import com.crediya.model.user.constants.ValidationMessages;
import com.crediya.model.user.exceptions.ExceptionMessages;
import com.crediya.model.user.exceptions.ValidationException;
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
	private final IUserMapper userMapper;

	public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
		log.info(LogMessages.CONSUMING_PATH_USERS);
		log.info(LogMessages.STARTING_TO_PROCESS_REQUEST);
		return serverRequest.bodyToMono(SaveUserDTO.class).map(userMapper::toModel).flatMap(userUseCase::save)
		 .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED).build())
		 .onErrorResume(ValidationException.class,
			ex -> ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
			.bodyValue(new CustomErrorResponse(ExceptionMessages.VALIDATION_ERROR, ex.getMessage())));
	}
}
