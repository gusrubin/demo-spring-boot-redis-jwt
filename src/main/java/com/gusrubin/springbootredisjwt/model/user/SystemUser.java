package com.gusrubin.springbootredisjwt.model.user;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@RedisHash("SpringBootJwtSystemUser")
@Data
@Builder
public class SystemUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Indexed
	private String username;
	@JsonIgnore
	private String password;
	private String name;
	private String lastName;
	private String email;
	@Indexed
	private String role;
	Boolean isEnabled;
	private Date lastLogin;

}
