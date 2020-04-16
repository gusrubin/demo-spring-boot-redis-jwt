package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import org.mapstruct.Mapper;

import com.gusrubin.springbootredisjwt.model.user.SystemUser;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {

	SystemUserResponseDto systemUserToSystemUserResponseDto(SystemUser systemUser);

	SystemUser systemUserRequestDtoTosystemUser(SystemUserRequestDto systemUserRequestDto);

}
