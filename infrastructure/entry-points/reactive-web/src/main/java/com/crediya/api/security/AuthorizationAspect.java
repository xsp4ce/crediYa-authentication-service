package com.crediya.api.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Arrays;

@Aspect
@Component
public class AuthorizationAspect {

	@Around("@annotation(requireRole)")
	public Object checkAuthorization(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
		Object[] args = joinPoint.getArgs();
		ServerRequest request = null;

		for (Object arg : args) {
			if (arg instanceof ServerRequest) {
				request = (ServerRequest) arg;
				break;
			}
		}

		if (request == null) {
			return joinPoint.proceed();
		}

		String userRole = request.exchange().getAttribute("userRole");

		if (userRole == null || !Arrays.asList(requireRole.value()).contains(userRole)) {
			return ServerResponse.status(HttpStatus.FORBIDDEN)
			 .bodyValue("{\"code\":\"FORBIDDEN\",\"message\":\"Insufficient permissions\"}");
		}

		return joinPoint.proceed();
	}
}
