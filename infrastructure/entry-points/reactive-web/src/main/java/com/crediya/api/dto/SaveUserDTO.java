package com.crediya.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveUserDTO(String documentNumber, String name, String lastName, LocalDate birthDate, String address,
													String phone, String email, BigDecimal baseSalary) {
}
