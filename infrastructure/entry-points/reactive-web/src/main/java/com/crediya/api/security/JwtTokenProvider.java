package com.crediya.api.security;

import com.crediya.model.login.Token;
import com.crediya.model.login.gateways.TokenRepository;
import com.crediya.model.role.RoleEnum;
import com.crediya.model.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtTokenProvider implements TokenRepository {

	private final Algorithm algorithm;
	private final String issuer;
	private final long ttlMinutes;
	private final Clock clock;

	public JwtTokenProvider(String hmacSecret, String issuer, long ttlMinutes, Clock clock) {
		this.algorithm = Algorithm.HMAC256(hmacSecret);
		this.issuer = issuer;
		this.ttlMinutes = ttlMinutes;
		this.clock = clock;
	}

	@Override
	public Mono<Token> generateFor(User user) {
		return Mono.fromCallable(() -> {
			Instant now = clock.instant();
			Instant exp = now.plus(ttlMinutes, ChronoUnit.MINUTES);
			RoleEnum roleEnum = RoleEnum.fromId(user.getRoleId());

			String jwt =
			 JWT
				.create()
				.withIssuer(issuer)
				.withSubject(user.getId().toString())
				.withClaim("role", roleEnum.getName())
				.withIssuedAt(Date.from(now))
				.withExpiresAt(Date.from(exp))
				.sign(algorithm);

			return new Token(jwt, exp);
		});
	}
}
