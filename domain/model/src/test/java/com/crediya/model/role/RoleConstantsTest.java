package com.crediya.model.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleConstantsTest {

	@Test
	void shouldHaveExpectedRoleConstants() {
		assertEquals("ADMINISTRATOR", RoleConstants.ADMINISTRATOR);
		assertEquals("ADVISOR", RoleConstants.ADVISOR);
		assertEquals("CUSTOMER", RoleConstants.CUSTOMER);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, RoleConstants::new);
	}

	@Test
	void shouldHaveConsistentValues() {
		assertNotNull(RoleConstants.ADMINISTRATOR);
		assertNotNull(RoleConstants.ADVISOR);
		assertNotNull(RoleConstants.CUSTOMER);

		assertFalse(RoleConstants.ADMINISTRATOR.isEmpty());
		assertFalse(RoleConstants.ADVISOR.isEmpty());
		assertFalse(RoleConstants.CUSTOMER.isEmpty());
	}

	@Test
	void shouldHaveUniqueValues() {
		assertNotEquals(RoleConstants.ADMINISTRATOR, RoleConstants.ADVISOR);
		assertNotEquals(RoleConstants.ADMINISTRATOR, RoleConstants.CUSTOMER);
		assertNotEquals(RoleConstants.ADVISOR, RoleConstants.CUSTOMER);
	}
}
