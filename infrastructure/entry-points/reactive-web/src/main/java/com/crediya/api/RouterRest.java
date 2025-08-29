package com.crediya.api;

import com.crediya.api.constants.UserPaths;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.exception.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
	@Bean
	@RouterOperations({
	 @RouterOperation(path = "/api/v1/users", method = RequestMethod.POST, operation = @Operation(operationId =
		"createUser", summary = "Create a new user", description =
		"Creates a new user in the system with the provided " + "information", tags = {
		"User Management"}, requestBody = @RequestBody(description = "User information to create", required = true,
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation =
		 SaveUserDTO.class), examples = @ExampleObject(name = "User Example", value = """
		{
		    "documentNumber": "12345678",
		    "name": "John",
		    "lastName": "Doe",
		    "birthDate": "1990-05-15",
		    "address": "Calle 123 #45-67, Bogotá",
		    "phone": "3001234567",
		    "email": "john.doe@example.com",
		    "baseSalary": 5000000.00
		}
		"""))), responses = {
		@ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType =
		 MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "400", description = "Validation error or bad request", content = @Content(mediaType =
		 MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorResponse.class), examples =
		@ExampleObject(name = "Validation Error", value = """
		 	{
		 		"code": "VALIDATION_ERROR",
		 		"message": "Field is already registered"
		 	}
		 """)))}))})
	public RouterFunction<ServerResponse> routerFunction(Handler handler) {
		return route(POST(UserPaths.USERS), handler::listenSaveUser);
	}
}
