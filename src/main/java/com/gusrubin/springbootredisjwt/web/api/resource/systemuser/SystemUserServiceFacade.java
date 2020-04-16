package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gusrubin.springbootredisjwt.model.user.SystemUser;
import com.gusrubin.springbootredisjwt.model.user.SystemUserService;

@Component
public class SystemUserServiceFacade {

	private final SystemUserService systemUserService;
	private final SystemUserMapper systemUserMapper;

	@Autowired
	public SystemUserServiceFacade(SystemUserService systemUserService, SystemUserMapper systemUserMapper) {
		this.systemUserService = systemUserService;
		this.systemUserMapper = systemUserMapper;
	}

	public List<SystemUserResponseDto> getAllManagerUsers() {
		List<SystemUserResponseDto> systemUserResponseDtoList = new ArrayList<>();
		systemUserService.getAllSystemUsers()
				.forEach(m -> systemUserResponseDtoList.add(systemUserMapper.systemUserToSystemUserResponseDto(m)));
		return systemUserResponseDtoList;
	}

	public SystemUserResponseDto getOwnManagerUser() {
		return systemUserMapper.systemUserToSystemUserResponseDto(systemUserService.getOwnManagerUser());
	}

	public SystemUserResponseDto getManagerUser() {
		return systemUserMapper.systemUserToSystemUserResponseDto(systemUserService.getOwnManagerUser());
	}

	public SystemUserResponseDto createManagerUser(SystemUserRequestDto systemUserRequestDto) {
		SystemUser systemUser = systemUserMapper.systemUserRequestDtoTosystemUser(systemUserRequestDto);
		switch (systemUser.getRole().strip().toLowerCase()) {
		case "manager":
			systemUser = systemUserService.createManagerUser(systemUser);
			break;
		case "receptionist":
			systemUser = systemUserService.createReceptionistUser(systemUser);
			break;
		case "monitor":
			systemUser = systemUserService.createMonitorUser(systemUser);
			break;
		default:
			throw new IllegalArgumentException("Invalid type of user");
		}
		return systemUserMapper.systemUserToSystemUserResponseDto(systemUser);
	}

	public SystemUserResponseDto updateSystemUser(String username, SystemUserRequestDto systemUserRequestDto) {
		SystemUser newSystemUser = systemUserMapper.systemUserRequestDtoTosystemUser(systemUserRequestDto);
		return systemUserMapper
				.systemUserToSystemUserResponseDto(systemUserService.updateSystemUser(username, newSystemUser));
	}

	public void deleteManagerUser(String username) {
		systemUserService.deleteSystemUser(username);
	}

}
