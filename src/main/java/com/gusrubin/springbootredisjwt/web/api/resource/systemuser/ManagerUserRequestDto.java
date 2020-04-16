package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ManagerUserRequestDto {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String role;
	private Boolean isEnabled;
	@NotBlank
	private String name;
	@NotBlank
	private String lastName;
	@NotBlank
	private String email;

}
