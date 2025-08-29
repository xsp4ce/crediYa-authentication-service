package com.crediya.api.exception;

import com.crediya.model.user.exceptions.ExceptionMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "Error Response", description = "Standard error response format")
public class CustomErrorResponse {

	@Schema(description = "Error code identifying the type of error", example = ExceptionMessages.VALIDATION_ERROR)
	private String code;

	@Schema(description = "Human-readable error message", example = ExceptionMessages.FIELD_ALREADY_REGISTERED)
	private String message;
}
