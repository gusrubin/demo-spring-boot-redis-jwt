package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import lombok.Getter;

public class LoginResponseDto {

	@Getter
	private final String jwt;

	public LoginResponseDto(String jwt) {
		this.jwt = jwt;
	}

}
