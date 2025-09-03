package com.crediya.model.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

	@Test
	void shouldCreateLoginWithBuilder() {
		Login login = Login.builder().email("test@example.com").password("password123").build();

		assertNotNull(login);
		assertEquals("test@example.com", login.getEmail());
		assertEquals("password123", login.getPassword());
	}

	@Test
	void shouldCreateLoginWithNoArgsConstructor() {
		Login login = new Login();

		assertNotNull(login);
		assertNull(login.getEmail());
		assertNull(login.getPassword());
	}

	@Test
	void shouldCreateLoginWithAllArgsConstructor() {
		Login login = new Login("user@domain.com", "securepass");

		assertEquals("user@domain.com", login.getEmail());
		assertEquals("securepass", login.getPassword());
	}

	@Test
	void shouldModifyLoginFields() {
		Login login = new Login();
		login.setEmail("modified@example.com");
		login.setPassword("newpassword");

		assertEquals("modified@example.com", login.getEmail());
		assertEquals("newpassword", login.getPassword());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		Login original = Login.builder().email("original@example.com").password("originalpass").build();

		Login copy = original.toBuilder().email("copy@example.com").build();

		assertEquals("copy@example.com", copy.getEmail());
		assertEquals("originalpass", copy.getPassword());
		assertEquals("original@example.com", original.getEmail());
	}
}