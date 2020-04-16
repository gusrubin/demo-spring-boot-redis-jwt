package com.gusrubin.springbootredisjwt.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemUserDetailsService implements UserDetailsService {

	private final SystemUserRepository systemUserRepository;

	@Autowired
	public SystemUserDetailsService(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) {
		SystemUser systemUser = systemUserRepository.findByUsername(username);
		if (systemUser == null) {
			throw new UsernameNotFoundException(username);
		}
		return new SystemUserDetails(systemUser);
	}

}
