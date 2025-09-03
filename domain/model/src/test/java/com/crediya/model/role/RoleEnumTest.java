package com.crediya.model.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleEnumTest {

	@Test
	void shouldHaveThreeRoleValues() {
		RoleEnum[] roles = RoleEnum.values();
		assertEquals(3, roles.length);
	}

	@Test
	void shouldHaveCorrectAdministratorRole() {
		RoleEnum admin = RoleEnum.ADMINISTRATOR;

		assertEquals(1L, admin.getId());
		assertEquals(RoleConstants.ADMINISTRATOR, admin.getName());
		assertEquals("Administrator with full access", admin.getDescription());
	}

	@Test
	void shouldHaveCorrectAdvisorRole() {
		RoleEnum advisor = RoleEnum.ADVISOR;

		assertEquals(2L, advisor.getId());
		assertEquals(RoleConstants.ADVISOR, advisor.getName());
		assertEquals("Advisor with limited access", advisor.getDescription());
	}

	@Test
	void shouldHaveCorrectCustomerRole() {
		RoleEnum customer = RoleEnum.CUSTOMER;

		assertEquals(3L, customer.getId());
		assertEquals(RoleConstants.CUSTOMER, customer.getName());
		assertEquals("Customer with access to own data", customer.getDescription());
	}

	@Test
	void shouldFindRoleById() {
		assertEquals(RoleEnum.ADMINISTRATOR, RoleEnum.fromId(1L));
		assertEquals(RoleEnum.ADVISOR, RoleEnum.fromId(2L));
		assertEquals(RoleEnum.CUSTOMER, RoleEnum.fromId(3L));
	}

	@Test
	void shouldThrowExceptionForInvalidId() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromId(99L));

		assertEquals("Invalid role id: 99", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionForNullId() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromId(null));

		assertEquals("Invalid role id: null", exception.getMessage());
	}

	@Test
	void shouldHaveUniqueIds() {
		RoleEnum[] roles = RoleEnum.values();

		for (int i = 0; i < roles.length; i++) {
			for (int j = i + 1; j < roles.length; j++) {
				assertNotEquals(roles[i].getId(), roles[j].getId(), "Role IDs should be unique");
			}
		}
	}

	@Test
	void shouldHaveUniqueNames() {
		RoleEnum[] roles = RoleEnum.values();

		for (int i = 0; i < roles.length; i++) {
			for (int j = i + 1; j < roles.length; j++) {
				assertNotEquals(roles[i].getName(), roles[j].getName(), "Role names should be unique");
			}
		}
	}

	@Test
	void shouldReturnProperValueOf() {
		assertEquals(RoleEnum.ADMINISTRATOR, RoleEnum.valueOf("ADMINISTRATOR"));
		assertEquals(RoleEnum.ADVISOR, RoleEnum.valueOf("ADVISOR"));
		assertEquals(RoleEnum.CUSTOMER, RoleEnum.valueOf("CUSTOMER"));
	}

	@Test
	void shouldThrowExceptionForInvalidValueOf() {
		assertThrows(IllegalArgumentException.class, () -> RoleEnum.valueOf("INVALID_ROLE"));
	}
}
