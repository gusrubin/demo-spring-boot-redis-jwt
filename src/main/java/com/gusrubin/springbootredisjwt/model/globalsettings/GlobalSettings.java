package com.gusrubin.springbootredisjwt.model.globalsettings;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@RedisHash("SpringJwt2GlobalSettings")
@Data
@Builder
public class GlobalSettings {

	@Id
	String id;
	String jwtSecret;
	Long jwtExpirationSeconds;

}
