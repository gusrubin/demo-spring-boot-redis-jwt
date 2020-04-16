package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class SystemUserResponseDto {

	private String username;
	private String role;
	private Boolean isEnabled;
	private String name;
	private String lastName;
	private String email;
	private Date lastLogin;

}
