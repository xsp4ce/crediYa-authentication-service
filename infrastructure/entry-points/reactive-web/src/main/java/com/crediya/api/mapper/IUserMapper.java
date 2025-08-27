package com.crediya.api.mapper;

import com.crediya.api.dto.SaveUserDTO;
import com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
	User toModel(SaveUserDTO saveUserDTO);
}
