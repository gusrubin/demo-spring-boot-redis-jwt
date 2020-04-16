package com.gusrubin.springbootredisjwt.web.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicCredential {

	private String username;
	private String password;

}
