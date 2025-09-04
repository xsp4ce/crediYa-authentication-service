package com.crediya.model.role;

public enum RoleEnum {
	ADMINISTRATOR(1L, RoleConstants.ADMINISTRATOR, "Administrator with full access"),
	ADVISOR(2L, RoleConstants.ADVISOR, "Advisor with limited access"),
	CUSTOMER(3L, RoleConstants.CUSTOMER, "Customer with access to own data");

	private final Long id;
	private final String name;
	private final String description;

	RoleEnum(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static RoleEnum fromId(Long id) {
		for (RoleEnum role : values()) {
			if (role.id.equals(id)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Invalid role id: " + id);
	}
}
