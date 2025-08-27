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

	@Column("document_number")
	private String documentNumber;

	private String name;

	@Column("last_name")
	private String lastName;

	@Column("birth_date")
	private LocalDate birthDate;

	private String address;
	private String phone;
	private String email;

	@Column("base_salary")
	private BigDecimal baseSalary;
}