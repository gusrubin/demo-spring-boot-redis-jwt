package com.gusrubin.springbootredisjwt.model.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SystemUserService {

	private final SystemUserRepository systemUserRepository;
	private final PasswordEncoder passwordEncoder;
	@Value("${spring-boot-redis-jwt.default-admin-password}")
	private String defaultAdminPassword;
	private static final String ADMIN_USERNAME = "admin";

	@Autowired
	public SystemUserService(SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
		this.systemUserRepository = systemUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// User querying

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public List<SystemUser> getAllSystemUsers() {
		List<SystemUser> managerUserList = (List<SystemUser>) systemUserRepository.findAll();
		if (ERole.ROLE_MANAGER.label.equals(getResquesterUsername())) {
			managerUserList.removeIf(m -> ERole.ROLE_ADMIN.label.equals(m.getUsername()));
		}
		return managerUserList;
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('RECEPTIONIST') or hasRole('MONITOR')")
	public SystemUser getOwnManagerUser() {
		return findSystemUser(getResquesterUsername());
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public SystemUser getSystemUser(String username) {
		SystemUser systemUser = findSystemUser(username);
		if (ERole.ROLE_MANAGER.label.equals(getResquesterUsername())
				&& ERole.ROLE_ADMIN.name().equals(systemUser.getRole())) {
			throw new AccessDeniedException("Action restrited to 'admim' user");
		}
		return findSystemUser(getResquesterUsername());
	}

	private SystemUser findSystemUser(String username) {
		SystemUser managerUserResul = systemUserRepository.findByUsername(username);
		if (managerUserResul == null) {
			throw new IllegalStateException("User doesn't exist");
		}
		return managerUserResul;
	}

	// User creation

	@PreAuthorize("hasRole('ADMIN')")
	public SystemUser createManagerUser(SystemUser systemUser) {
		systemUser.setRole(ERole.ROLE_MANAGER.name());
		return doCreate(systemUser);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public SystemUser createReceptionistUser(SystemUser systemUser) {
		systemUser.setRole(ERole.ROLE_RECEPTIONIST.name());
		return doCreate(systemUser);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public SystemUser createMonitorUser(SystemUser systemUser) {
		systemUser.setRole(ERole.ROLE_MONITOR.name());
		return doCreate(systemUser);
	}

	private SystemUser doCreate(SystemUser systemUser) {
		systemUser.setUsername(systemUser.getUsername().strip().toLowerCase());
		systemUser.setName(StringUtils.capitalize(systemUser.getName().strip()));
		systemUser.setLastName(StringUtils.capitalize(systemUser.getLastName().strip()));
		systemUser.setEmail(systemUser.getEmail().strip().toLowerCase());
		systemUser.setIsEnabled(false);
		return systemUserRepository.save(systemUser);
	}

	// User updating

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public SystemUser updateSystemUser(String username, SystemUser newManagerUser) {
		SystemUser systemUser = findSystemUser(username);
		if (ERole.ROLE_ADMIN.name().equals(systemUser.getRole())) {
			throw new AccessDeniedException("Can't update 'admin' user");
		}
		if (!ERole.ROLE_ADMIN.label.equals(getResquesterUsername())
				&& ERole.ROLE_MANAGER.name().equals(systemUser.getRole())) {
			throw new AccessDeniedException("Only the 'admin' can update a 'manager' user");
		}
		return doUpdate(systemUser, newManagerUser);
	}

	private SystemUser doUpdate(SystemUser systemUser, SystemUser newManagerUser) {
		if (StringUtils.hasText(newManagerUser.getName()) && !newManagerUser.getName().equals(systemUser.getName())) {
			systemUser.setName(StringUtils.capitalize(newManagerUser.getName().strip()));
		}
		if (StringUtils.hasText(newManagerUser.getLastName())
				&& !newManagerUser.getLastName().equals(systemUser.getLastName())) {
			systemUser.setLastName(StringUtils.capitalize(newManagerUser.getLastName().strip()));
		}
		if (StringUtils.hasText(newManagerUser.getEmail())
				&& !newManagerUser.getEmail().equals(systemUser.getEmail())) {
			systemUser.setEmail(newManagerUser.getEmail().strip());
		}
		systemUser.setIsEnabled(true);
		return systemUserRepository.save(systemUser);
	}

	// User removing

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public void deleteSystemUser(String username) {
		SystemUser systemUser = findSystemUser(username);
		if (ERole.ROLE_ADMIN.name().equals(systemUser.getRole())) {
			throw new AccessDeniedException("Can't remove user 'admin'");
		}
		if (!ERole.ROLE_ADMIN.label.equals(getResquesterUsername())
				&& ERole.ROLE_MANAGER.name().equals(systemUser.getRole())) {
			throw new AccessDeniedException("Only the 'admin' user can remove a 'manager' user");
		}
		systemUserRepository.delete(systemUser);
	}

	public void checkAdminUser() {
		if (systemUserRepository.findByUsername(ADMIN_USERNAME) == null) {
			SystemUser adminUser = SystemUser.builder().username(ADMIN_USERNAME)
					.password(passwordEncoder.encode(defaultAdminPassword)).role(ERole.ROLE_ADMIN.name())
					.isEnabled(true).build();
			systemUserRepository.save(adminUser);
		}
	}

	private String getResquesterUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
