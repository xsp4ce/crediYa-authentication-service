package com.crediya.api;

import com.crediya.api.constants.UserPaths;
import com.crediya.api.dto.LoginUserDTO;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.dto.ValidateDocumentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
	@Bean
	@RouterOperations({
	 @RouterOperation(path = UserPaths.USERS, method = RequestMethod.POST, beanMethod = "listenSaveUser", operation =
	 @Operation(operationId = "saveUser", summary = "Create a new user", tags = {
		"User Management"}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation =
		SaveUserDTO.class))), responses = @ApiResponse(responseCode = "201"))),
	 @RouterOperation(path = UserPaths.DOCUMENT, method = RequestMethod.POST, beanMethod = "listenValidateDocument",
		operation = @Operation(operationId = "validateDocument", summary = "Validate document number", tags = {
		"User Management"}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation =
		 ValidateDocumentDTO.class))), responses = @ApiResponse(responseCode = "201"))),
	 @RouterOperation(path = UserPaths.LOGIN, method = RequestMethod.POST, beanMethod = "listenLoginUser", operation =
	 @Operation(operationId = "loginUser", summary = "User authentication", tags = {
		"User Management"}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation =
		LoginUserDTO.class))), responses = @ApiResponse(responseCode = "201")))})
	public RouterFunction<ServerResponse> routerFunction(Handler handler) {
		return route(POST(UserPaths.USERS), handler::listenSaveUser)
		 .andRoute(POST(UserPaths.DOCUMENT), handler::listenValidateDocument)
		 .andRoute(POST(UserPaths.LOGIN), handler::listenLoginUser);
	}
}
