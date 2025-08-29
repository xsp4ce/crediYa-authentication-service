package com.crediya.api.dto;

import com.crediya.model.user.constants.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Save User DTO", description = "Data Transfer Object for creating a new user")
public record SaveUserDTO(
 @Schema(description = "User's first name", example = "John", minLength = 1, maxLength = 50)
 @NotBlank(message = ValidationMessages.NAME_REQUIRED)
 @Size(max = 50, message = ValidationMessages.NAME_MAX_LENGTH)
 String name,

 @Schema(description = "User's last name", example = "Doe", minLength = 1, maxLength = 50)
 @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
 @Size(max = 50, message = ValidationMessages.LAST_NAME_MAX_LENGTH)
 String lastName,

 @Schema(description = "User's birth date", example = "1990-05-15")
 LocalDate birthDate,

 @Schema(description = "User's address", example = "Calle 123 #45-67, Bogotá", maxLength = 200)
 String address,

 @Schema(description = "User's phone number", example = "3001234567")
 String phone,

 @Schema(description = "User's email address", example = "john.doe@example.com")
 @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
 @Email(message = ValidationMessages.EMAIL_INVALID_FORMAT)
 String email,

 @Schema(description = "User's base salary in Colombian Pesos", example = "5000000.00", minimum = "1")
 @NotNull(message = ValidationMessages.BASE_SALARY_REQUIRED)
 @DecimalMin(value = "1", message = ValidationMessages.BASE_SALARY_MUST_BE_POSITIVE)
 BigDecimal baseSalary,

 @Schema(description = "User's document number", example = "12345678")
 String documentNumber
) {}
