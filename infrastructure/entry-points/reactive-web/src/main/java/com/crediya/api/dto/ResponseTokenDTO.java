package com.crediya.api.dto;

import com.crediya.model.login.Token;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Response Token DTO", description = "Response containing authentication token")
public record ResponseTokenDTO(@Schema(description = "Authentication token information") Token token) {
}
