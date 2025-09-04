package com.crediya.model.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class UserTest {

	private User user;

	@BeforeEach
	void setUp() {
		user = User.builder().id(1L).documentNumber("12345678").firstName("John").lastName("Doe")
		 .birthDate(LocalDate.of(1990, 1, 1)).address("123 Main St").phone("555-1234").email("john.doe@example.com")
		 .password("password123").roleId(1L).baseSalary(new BigDecimal("50000")).build();
	}

	@Test
	void shouldCreateUserWithBuilder() {
		assertNotNull(user);
		assertEquals(1L, user.getId());
		assertEquals("12345678", user.getDocumentNumber());
		assertEquals("John", user.getFirstName());
		assertEquals("Doe", user.getLastName());
		assertEquals(LocalDate.of(1990, 1, 1), user.getBirthDate());
		assertEquals("123 Main St", user.getAddress());
		assertEquals("555-1234", user.getPhone());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals("password123", user.getPassword());
		assertEquals(1L, user.getRoleId());
		assertEquals(new BigDecimal("50000"), user.getBaseSalary());
	}

	@Test
	void shouldCreateUserWithNoArgsConstructor() {
		User emptyUser = new User();
		assertNotNull(emptyUser);
		assertNull(emptyUser.getId());
		assertNull(emptyUser.getFirstName());
	}

	@Test
	void shouldCreateUserWithAllArgsConstructor() {
		User userWithAllArgs =
		 new User(2L, "87654321", "Jane", "Smith", LocalDate.of(1985, 5, 15), "456 Oak Ave", "555-5678", "jane" +
			".smith@example.com", "securepass", 2L, new BigDecimal("60000"));

		assertEquals(2L, userWithAllArgs.getId());
		assertEquals("Jane", userWithAllArgs.getFirstName());
		assertEquals("Smith", userWithAllArgs.getLastName());
		assertEquals(new BigDecimal("60000"), userWithAllArgs.getBaseSalary());
	}

	@Test
	void shouldModifyUserFields() {
		user.setFirstName("UpdatedName");
		user.setBaseSalary(new BigDecimal("75000"));

		assertEquals("UpdatedName", user.getFirstName());
		assertEquals(new BigDecimal("75000"), user.getBaseSalary());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		User copiedUser = user.toBuilder().firstName("ModifiedJohn").baseSalary(new BigDecimal("55000")).build();

		assertEquals("ModifiedJohn", copiedUser.getFirstName());
		assertEquals(new BigDecimal("55000"), copiedUser.getBaseSalary());
		assertEquals(user.getLastName(), copiedUser.getLastName());
		assertEquals(user.getEmail(), copiedUser.getEmail());
	}
}
