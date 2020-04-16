package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gusrubin.lab.springbootjwt.web.security.BasicCredential;
import com.gusrubin.lab.springbootjwt.web.security.JwtProvider;
import com.gusrubin.lab.springbootjwt.web.security.WebAuthenticationProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class LoginController {

	private final WebAuthenticationProvider webAuthenticationProvider;
	private final JwtProvider jwtProvider;

	@Autowired
	public LoginController(WebAuthenticationProvider webAuthenticationProvider, JwtProvider jwtProvider) {
		this.webAuthenticationProvider = webAuthenticationProvider;
		this.jwtProvider = jwtProvider;
	}

	@GetMapping("/login")
	public ResponseEntity<Object> getJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

		BasicCredential basicCredential = webAuthenticationProvider.getBasicCredentialFromHeader(authorizationHeader);
		log.info("[BasicCredential]=(username=" + basicCredential.getUsername() + ", password="
				+ basicCredential.getPassword());

		Authentication authentication = webAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(basicCredential.getUsername(), basicCredential.getPassword()));

		return ResponseEntity.ok(new LoginResponseDto(jwtProvider.generateJwt(authentication)));
	}

}
