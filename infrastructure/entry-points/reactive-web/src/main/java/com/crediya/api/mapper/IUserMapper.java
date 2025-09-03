package com.crediya.api.mapper;

import com.crediya.api.dto.LoginUserDTO;
import com.crediya.api.dto.SaveUserDTO;
import com.crediya.api.dto.ValidateDocumentDTO;
import com.crediya.model.command.ValidateDocumentCommand;
import com.crediya.model.login.Login;
import com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
	User toModel(SaveUserDTO saveUserDTO);
	Login toLoginModel(LoginUserDTO loginUserDTO);
	ValidateDocumentCommand toCommand(ValidateDocumentDTO validateDocumentDTO);
}
