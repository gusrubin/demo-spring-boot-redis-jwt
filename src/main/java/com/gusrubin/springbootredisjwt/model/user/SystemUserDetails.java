package com.gusrubin.springbootredisjwt.model.user;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SystemUserDetails implements UserDetails {

	private static final long serialVersionUID = 1269185834135207542L;
	private final SystemUser systemUser;

	public SystemUserDetails(final SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(systemUser.getRole()));
		return authorities;
	}

	public String getName() {
		return systemUser.getName();
	}

	public String getLastName() {
		return systemUser.getLastName();
	}

	public String getEmail() {
		return systemUser.getEmail();
	}

	@Override
	public String getUsername() {
		return systemUser.getUsername();
	}

	@Override
	public String getPassword() {
		return systemUser.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return systemUser.getIsEnabled();
	}

	public SystemUser getManagerUser() {
		return systemUser;
	}

}
