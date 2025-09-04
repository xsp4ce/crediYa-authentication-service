package com.crediya.model.login;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

	@Test
	void shouldCreateTokenWithValueAndExpiration() {
		String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
		Instant expiresAt = Instant.now().plusSeconds(3600);

		Token token = new Token(tokenValue, expiresAt);

		assertEquals(tokenValue, token.value());
		assertEquals(expiresAt, token.expiresAt());
	}

	@Test
	void shouldHandleNullValues() {
		Token token = new Token(null, null);

		assertNull(token.value());
		assertNull(token.expiresAt());
	}

	@Test
	void shouldBeEqualWhenSameValues() {
		String value = "token123";
		Instant expiration = Instant.now();

		Token token1 = new Token(value, expiration);
		Token token2 = new Token(value, expiration);

		assertEquals(token1, token2);
		assertEquals(token1.hashCode(), token2.hashCode());
	}

	@Test
	void shouldNotBeEqualWhenDifferentValues() {
		Instant now = Instant.now();
		Token token1 = new Token("token1", now);
		Token token2 = new Token("token2", now);

		assertNotEquals(token1, token2);
	}

	@Test
	void shouldHaveProperToString() {
		Token token = new Token("test-token", Instant.parse("2024-01-01T00:00:00Z"));
		String toString = token.toString();

		assertTrue(toString.contains("test-token"));
		assertTrue(toString.contains("2024-01-01T00:00:00Z"));
	}
}
