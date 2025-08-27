package com.crediya.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CrediYa Authentication Service API", version = "1.0.0", description = "API " +
 "for user authentication and management in CrediYa platform", contact = @Contact(name = "CrediYa Development Team",
 email = "dev@crediya.com"), license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")),
 servers = {
 @Server(url = "http://localhost:8080", description = "Local Development Server"),
 @Server(url = "https://api.crediya.com", description = "Production Server")})
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("users").displayName("User Management").pathsToMatch("/api/v1/users/**")
		 .build();
	}
}