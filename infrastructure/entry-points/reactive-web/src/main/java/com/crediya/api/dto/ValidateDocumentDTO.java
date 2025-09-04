package com.crediya.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Validate Document DTO", description = "Data Transfer Object for document validation")
public record ValidateDocumentDTO(
 @Schema(description = "Document number to validate", example = "12345678") String documentNumber,

 @Schema(description = "User ID associated with the document", example = "1") Long idUser) {
}
