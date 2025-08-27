package com.crediya.api.mapper;

import com.crediya.api.dto.SaveUserDTO;
import com.crediya.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {IUserMapperImpl.class})
class UserMapperTest {

	@Autowired
	private IUserMapper userMapper;

	@Test
	void shouldMapSaveUserDTOToUser() {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "John", "Doe", LocalDate.of(1990, 5, 15), "Calle 123 #45-67, Bogotá", "3001234567",
			"john.doe@example.com", BigDecimal.valueOf(5000000.00));

		User user = userMapper.toModel(saveUserDTO);

		assertThat(user).isNotNull();
		assertThat(user.getDocumentNumber()).isEqualTo("12345678");
		assertThat(user.getName()).isEqualTo("John");
		assertThat(user.getLastName()).isEqualTo("Doe");
		assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, 5, 15));
		assertThat(user.getAddress()).isEqualTo("Calle 123 #45-67, Bogotá");
		assertThat(user.getPhone()).isEqualTo("3001234567");
		assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
		assertThat(user.getBaseSalary()).isEqualTo(BigDecimal.valueOf(5000000.00));
		assertThat(user.getId()).isNull(); // ID should not be mapped from DTO
	}

	@Test
	void shouldMapSaveUserDTOWithNullValues() {
		SaveUserDTO saveUserDTO = new SaveUserDTO(null, null, null, null, null, null, null, null);

		User user = userMapper.toModel(saveUserDTO);

		assertThat(user).isNotNull();
		assertThat(user.getDocumentNumber()).isNull();
		assertThat(user.getName()).isNull();
		assertThat(user.getLastName()).isNull();
		assertThat(user.getBirthDate()).isNull();
		assertThat(user.getAddress()).isNull();
		assertThat(user.getPhone()).isNull();
		assertThat(user.getEmail()).isNull();
		assertThat(user.getBaseSalary()).isNull();
		assertThat(user.getId()).isNull();
	}

	@Test
	void shouldMapSaveUserDTOWithMinimalData() {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("87654321", "Jane", "Smith", LocalDate.of(1985, 12, 25), "Main St 456", "9876543210", "jane" +
			".smith@test.com", BigDecimal.valueOf(3000000.50));

		User user = userMapper.toModel(saveUserDTO);

		assertThat(user).isNotNull();
		assertThat(user.getDocumentNumber()).isEqualTo("87654321");
		assertThat(user.getName()).isEqualTo("Jane");
		assertThat(user.getLastName()).isEqualTo("Smith");
		assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1985, 12, 25));
		assertThat(user.getAddress()).isEqualTo("Main St 456");
		assertThat(user.getPhone()).isEqualTo("9876543210");
		assertThat(user.getEmail()).isEqualTo("jane.smith@test.com");
		assertThat(user.getBaseSalary()).isEqualTo(BigDecimal.valueOf(3000000.50));
		assertThat(user.getId()).isNull();
	}

	@Test
	void shouldMapSaveUserDTOWithEmptyStrings() {
		SaveUserDTO saveUserDTO = new SaveUserDTO("", "", "", LocalDate.of(2000, 1, 1), "", "", "", BigDecimal.ZERO);

		User user = userMapper.toModel(saveUserDTO);

		assertThat(user).isNotNull();
		assertThat(user.getDocumentNumber()).isEqualTo("");
		assertThat(user.getName()).isEqualTo("");
		assertThat(user.getLastName()).isEqualTo("");
		assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
		assertThat(user.getAddress()).isEqualTo("");
		assertThat(user.getPhone()).isEqualTo("");
		assertThat(user.getEmail()).isEqualTo("");
		assertThat(user.getBaseSalary()).isEqualTo(BigDecimal.ZERO);
		assertThat(user.getId()).isNull();
	}

	@Test
	void shouldHandleSpecialCharactersInMapping() {
		SaveUserDTO saveUserDTO =
		 new SaveUserDTO("12345678", "José María", "González-Pérez", LocalDate.of(1992, 3, 8), "Carrera 7 #32-16, Apt. " +
			"501", "+57 301 234 5678", "jose.gonzalez@correo.co", BigDecimal.valueOf(4500000.99));

		User user = userMapper.toModel(saveUserDTO);

		assertThat(user).isNotNull();
		assertThat(user.getDocumentNumber()).isEqualTo("12345678");
		assertThat(user.getName()).isEqualTo("José María");
		assertThat(user.getLastName()).isEqualTo("González-Pérez");
		assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1992, 3, 8));
		assertThat(user.getAddress()).isEqualTo("Carrera 7 #32-16, Apt. 501");
		assertThat(user.getPhone()).isEqualTo("+57 301 234 5678");
		assertThat(user.getEmail()).isEqualTo("jose.gonzalez@correo.co");
		assertThat(user.getBaseSalary()).isEqualTo(BigDecimal.valueOf(4500000.99));
		assertThat(user.getId()).isNull();
	}
}