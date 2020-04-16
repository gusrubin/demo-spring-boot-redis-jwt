package com.gusrubin.springbootredisjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootRedisJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRedisJwtApplication.class, args);
	}

}
