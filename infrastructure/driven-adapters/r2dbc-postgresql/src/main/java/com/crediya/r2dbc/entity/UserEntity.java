package com.crediya.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
	@Id
	private Long id;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;

	@Column("email")
	private String email;

	@Column("password")
	private String password;

	@Column("role_id")
	private Long roleId;

	@Column("base_salary")
	private BigDecimal baseSalary;

	@Column("document_number")
	private String documentNumber;

	@Column("birth_date")
	private LocalDate birthDate;

	@Column("address")
	private String address;

	@Column("phone")
	private String phone;
}