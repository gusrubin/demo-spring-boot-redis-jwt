package com.gusrubin.springbootredisjwt.web.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gusrubin.springbootredisjwt.model.globalsettings.GlobalSettingsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private String jwtSecret;
	private Long jwtExpirationSeconds;

	@Autowired
	public JwtProvider(GlobalSettingsService globalSettingsService) {
		this.jwtSecret = globalSettingsService.findGlobalSettings().getJwtSecret();
		this.jwtExpirationSeconds = globalSettingsService.findGlobalSettings().getJwtExpirationSeconds();
	}

	public String generateJwt(Authentication authentication) {
		return Jwts.builder().setSubject(authentication.getName()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (jwtExpirationSeconds * 1000)))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUsernameFromJwt(String jwt) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
	}

	public boolean validateJwt(String jwt) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

}
