package com.crediya.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CrediYa Authentication Service API", version = "1.0.0", description =
 "Authentication and User Management API for CrediYa platform", contact = @Contact(name = "CrediYa Development Team",
 email = "dev@crediya.com"), license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")),
 servers = {
 @Server(url = "http://localhost:8080", description = "Local Development Server"),
 @Server(url = "https://api-dev.crediya.com", description = "Development Server"),
 @Server(url = "https://api.crediya.com", description = "Production Server")})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
}
