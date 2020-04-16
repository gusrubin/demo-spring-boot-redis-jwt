package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import org.mapstruct.Mapper;

import com.gusrubin.lab.springbootjwt.model.manageruser.ManagerUser;

@Mapper(componentModel = "spring")
public interface ManagerUserMapper {

	ManagerUserResponseDto managerUserToManagerUserResponseDto(ManagerUser managerUser);

	ManagerUser managerUserRequestDtoTomanagerUser(ManagerUserRequestDto managerUserRequestDto);

}
