package com.crediya.api.security;

import com.crediya.model.login.gateways.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.time.Clock;

@Configuration
@EnableAspectJAutoProxy
public class SecurityConfig {
	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	public TokenRepository tokenProvider(
	 @Value("${security.jwt.secret}") String secret,
	 @Value("${security.jwt.issuer}") String issuer,
	 @Value("${security.jwt.ttl-minutes}") long ttl,
	 Clock clock
	) {
		return new JwtTokenProvider(secret, issuer, ttl, clock);
	}
}