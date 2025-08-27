package com.crediya.model.user;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	@Test
	void shouldCreateUserWithBuilder() {
		LocalDate birthDate = LocalDate.of(1990, 1, 1);
		BigDecimal baseSalary = BigDecimal.valueOf(5000000);

		User user = User.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe").birthDate(birthDate)
		 .address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(baseSalary).build();

		assertEquals(1L, user.getId());
		assertEquals("12345678", user.getDocumentNumber());
		assertEquals("John", user.getName());
		assertEquals("Doe", user.getLastName());
		assertEquals(birthDate, user.getBirthDate());
		assertEquals("123 Main St", user.getAddress());
		assertEquals("1234567890", user.getPhone());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals(baseSalary, user.getBaseSalary());
	}

	@Test
	void shouldCreateUserWithNoArgsConstructor() {
		User user = new User();
		assertNotNull(user);
		assertNull(user.getId());
		assertNull(user.getName());
		assertNull(user.getLastName());
		assertNull(user.getBirthDate());
		assertNull(user.getAddress());
		assertNull(user.getPhone());
		assertNull(user.getEmail());
		assertNull(user.getBaseSalary());
		assertNull(user.getDocumentNumber());
	}

	@Test
	void shouldCreateUserWithAllArgsConstructor() {
		LocalDate birthDate = LocalDate.of(1990, 1, 1);
		BigDecimal baseSalary = BigDecimal.valueOf(5000000);

		User user =
		 new User(1L, "12345678", "John", "Doe", birthDate, "123 Main St", "1234567890", "john.doe@example.com",
			baseSalary);

		assertEquals(1L, user.getId());
		assertEquals("12345678", user.getDocumentNumber());
		assertEquals("John", user.getName());
		assertEquals("Doe", user.getLastName());
		assertEquals(birthDate, user.getBirthDate());
		assertEquals("123 Main St", user.getAddress());
		assertEquals("1234567890", user.getPhone());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals(baseSalary, user.getBaseSalary());
	}

	@Test
	void shouldModifyUserWithSetters() {
		User user = new User();
		LocalDate birthDate = LocalDate.of(1990, 1, 1);
		BigDecimal baseSalary = BigDecimal.valueOf(5000000);

		user.setId(1L);
		user.setDocumentNumber("12345678");
		user.setName("John");
		user.setLastName("Doe");
		user.setBirthDate(birthDate);
		user.setAddress("123 Main St");
		user.setPhone("1234567890");
		user.setEmail("john.doe@example.com");
		user.setBaseSalary(baseSalary);

		assertEquals(1L, user.getId());
		assertEquals("12345678", user.getDocumentNumber());
		assertEquals("John", user.getName());
		assertEquals("Doe", user.getLastName());
		assertEquals(birthDate, user.getBirthDate());
		assertEquals("123 Main St", user.getAddress());
		assertEquals("1234567890", user.getPhone());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals(baseSalary, user.getBaseSalary());
	}

	@Test
	void shouldCreateNewInstanceWithToBuilder() {
		User originalUser =
		 User.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
			.address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
			.build();

		User modifiedUser = originalUser.toBuilder().name("Jane").email("jane.doe@example.com").build();

		assertEquals("Jane", modifiedUser.getName());
		assertEquals("jane.doe@example.com", modifiedUser.getEmail());
		assertEquals(originalUser.getId(), modifiedUser.getId());
		assertEquals(originalUser.getLastName(), modifiedUser.getLastName());
		assertEquals(originalUser.getBirthDate(), modifiedUser.getBirthDate());
		assertEquals(originalUser.getAddress(), modifiedUser.getAddress());
		assertEquals(originalUser.getPhone(), modifiedUser.getPhone());
		assertEquals(originalUser.getBaseSalary(), modifiedUser.getBaseSalary());
		assertEquals(originalUser.getDocumentNumber(), modifiedUser.getDocumentNumber());

		assertEquals("John", originalUser.getName());
		assertEquals("john.doe@example.com", originalUser.getEmail());
	}

	@Test
	void shouldHandleNullValues() {
		User user =
		 User.builder().id(null).documentNumber(null).name(null).lastName(null).birthDate(null).address(null).phone(null)
			.email(null).baseSalary(null).build();

		assertNull(user.getId());
		assertNull(user.getDocumentNumber());
		assertNull(user.getName());
		assertNull(user.getLastName());
		assertNull(user.getBirthDate());
		assertNull(user.getAddress());
		assertNull(user.getPhone());
		assertNull(user.getEmail());
		assertNull(user.getBaseSalary());
	}

	@Test
	void shouldCreateBuilderFromExistingUser() {
		User originalUser =
		 User.builder().id(1L).documentNumber("12345678").name("John").lastName("Doe").birthDate(LocalDate.of(1990, 1, 1))
			.address("123 Main St").phone("1234567890").email("john.doe@example.com").baseSalary(BigDecimal.valueOf(5000000))
			.build();

		User newUser = originalUser.toBuilder().build();

		assertEquals(originalUser.getId(), newUser.getId());
		assertEquals(originalUser.getDocumentNumber(), newUser.getDocumentNumber());
		assertEquals(originalUser.getName(), newUser.getName());
		assertEquals(originalUser.getLastName(), newUser.getLastName());
		assertEquals(originalUser.getBirthDate(), newUser.getBirthDate());
		assertEquals(originalUser.getAddress(), newUser.getAddress());
		assertEquals(originalUser.getPhone(), newUser.getPhone());
		assertEquals(originalUser.getEmail(), newUser.getEmail());
		assertEquals(originalUser.getBaseSalary(), newUser.getBaseSalary());

		assertNotSame(originalUser, newUser);
	}
}