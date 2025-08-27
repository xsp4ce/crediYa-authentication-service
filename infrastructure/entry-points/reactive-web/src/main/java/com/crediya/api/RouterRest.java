package com.crediya.api;

import com.crediya.api.config.UserPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
	private final UserPaths userPaths;

	@Bean
	public RouterFunction<ServerResponse> routerFunction(Handler handler) {
		return route(POST(userPaths.getUser()), handler::listenSaveUser);
	}
}
