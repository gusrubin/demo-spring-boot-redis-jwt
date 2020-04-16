package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gusrubin.lab.springbootjwt.model.manageruser.ManagerUser;
import com.gusrubin.lab.springbootjwt.model.manageruser.ManagerUserService;

@Component
public class ManagerUserServiceFacade {

	private final ManagerUserService managerUserService;
	private final ManagerUserMapper managerUserMapper;

	@Autowired
	public ManagerUserServiceFacade(ManagerUserService managerUserService, ManagerUserMapper managerUserMapper) {
		this.managerUserService = managerUserService;
		this.managerUserMapper = managerUserMapper;
	}

	public List<ManagerUserResponseDto> getAllManagerUsers() {
		List<ManagerUserResponseDto> managerUserResponseDtoList = new ArrayList<>();
		managerUserService.getAllManagerUsers()
				.forEach(m -> managerUserResponseDtoList.add(managerUserMapper.managerUserToManagerUserResponseDto(m)));
		return managerUserResponseDtoList;
	}

	public ManagerUserResponseDto getOwnManagerUser() {
		return managerUserMapper.managerUserToManagerUserResponseDto(managerUserService.getOwnManagerUser());
	}

	public ManagerUserResponseDto getManagerUser() {
		return managerUserMapper.managerUserToManagerUserResponseDto(managerUserService.getOwnManagerUser());
	}

	public ManagerUserResponseDto createManagerUser(ManagerUserRequestDto managerUserRequestDto) {
		ManagerUser managerUser = managerUserMapper.managerUserRequestDtoTomanagerUser(managerUserRequestDto);
		switch (managerUser.getRole().strip().toLowerCase()) {
		case "manager":
			managerUser = managerUserService.createManagerUser(managerUser);
			break;
		case "receptionist":
			managerUser = managerUserService.createReceptionistUser(managerUser);
			break;
		case "monitor":
			managerUser = managerUserService.createMonitorUser(managerUser);
			break;
		default:
			throw new IllegalArgumentException("Tipo de usuário inválido");
		}
		return managerUserMapper.managerUserToManagerUserResponseDto(managerUser);
	}

	public ManagerUserResponseDto updateManagerUser(String username, ManagerUserRequestDto managerUserRequestDto) {
		ManagerUser newManagerUser = managerUserMapper.managerUserRequestDtoTomanagerUser(managerUserRequestDto);
		return managerUserMapper
				.managerUserToManagerUserResponseDto(managerUserService.updateManagerUser(username, newManagerUser));
	}

	public void deleteManagerUser(String username) {
		managerUserService.deleteManagerUser(username);
	}

}
