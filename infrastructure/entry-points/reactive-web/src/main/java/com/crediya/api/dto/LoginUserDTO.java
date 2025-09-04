package com.crediya.api.dto;

import com.crediya.model.user.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(title = "Login User DTO", description = "Data Transfer Object for user authentication")
public record LoginUserDTO(
 @Schema(description = "User's email address", example = "john.doe@example.com")
 @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
 @Email(message = ValidationMessages.EMAIL_INVALID_FORMAT)
 String email,

 @Schema(description = "User's password", example = "SecurePassword123!", minLength = 8)
 @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
 @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_LENGTH)
 String password
) {}
