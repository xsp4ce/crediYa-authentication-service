package com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Save User DTO", description = "Data Transfer Object for creating a new user")
public record SaveUserDTO(

 @Schema(description = "User's document number (8 digits)", example = "12345678", pattern = "^\\d{8}$") @NotBlank(message = "Document number is required") @Pattern(regexp = "^\\d{8}$", message = "Document number must be 8 digits") String documentNumber,

 @Schema(description = "User's first name", example = "John", minLength = 1, maxLength = 50) @NotBlank(message =
	"Name is required") @Size(max = 50, message = "Name must not exceed 50 characters") String name,

 @Schema(description = "User's last name", example = "Doe", minLength = 1, maxLength = 50) @NotBlank(message = "Last " +
	"name is required") @Size(max = 50, message = "Last name must not exceed 50 characters") String lastName,

 @Schema(description = "User's birth date (must be 18 or older)", example = "1990-05-15") @NotNull(message = "Birth " +
	"date is required") @Past(message = "Birth date must be in the past") LocalDate birthDate,

 @Schema(description = "User's address", example = "Calle 123 #45-67, Bogotá", maxLength = 200) @NotBlank(message =
	"Address is required") @Size(max = 200, message = "Address must not exceed 200 characters") String address,

 @Schema(description = "User's phone number", example = "3001234567", pattern = "^[0-9+\\-\\s]+$") @NotBlank(message
	= "Phone is required") @Pattern(regexp = "^[0-9+\\-\\s]+$", message = "Invalid phone format") String phone,

 @Schema(description = "User's email address", example = "john.doe@example.com") @NotBlank(message = "Email is " +
	"required") @Email(message = "Invalid email format") String email,

 @Schema(description = "User's base salary in Colombian Pesos", example = "5000000.00", minimum = "1") @NotNull(message = "Base salary is required") @DecimalMin(value = "1", message = "Base salary must be greater than 0") BigDecimal baseSalary) {
}