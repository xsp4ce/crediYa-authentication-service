package com.crediya.model.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

	@Test
	void shouldCreateRoleWithBuilder() {
		Role role = Role.builder().id(1L).name("ADMIN").description("Administrator role").build();

		assertNotNull(role);
		assertEquals(1L, role.getId());
		assertEquals("ADMIN", role.getName());
		assertEquals("Administrator role", role.getDescription());
	}

	@Test
	void shouldCreateRoleWithNoArgsConstructor() {
		Role role = new Role();

		assertNotNull(role);
		assertNull(role.getId());
		assertNull(role.getName());
		assertNull(role.getDescription());
	}

	@Test
	void shouldCreateRoleWithAllArgsConstructor() {
		Role role = new Role(2L, "USER", "Regular user role");

		assertEquals(2L, role.getId());
		assertEquals("USER", role.getName());
		assertEquals("Regular user role", role.getDescription());
	}

	@Test
	void shouldModifyRoleFields() {
		Role role = new Role();
		role.setId(3L);
		role.setName("MODERATOR");
		role.setDescription("Content moderator");

		assertEquals(3L, role.getId());
		assertEquals("MODERATOR", role.getName());
		assertEquals("Content moderator", role.getDescription());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		Role original = Role.builder().id(1L).name("ADMIN").description("Original description").build();

		Role copy = original.toBuilder().description("Modified description").build();

		assertEquals(1L, copy.getId());
		assertEquals("ADMIN", copy.getName());
		assertEquals("Modified description", copy.getDescription());
		assertEquals("Original description", original.getDescription());
	}
}
