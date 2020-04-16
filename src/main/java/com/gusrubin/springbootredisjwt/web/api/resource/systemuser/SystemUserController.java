package com.gusrubin.springbootredisjwt.web.api.resource.systemuser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemUserController {

	private final SystemUserServiceFacade systemUserServiceFacade;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SystemUserController(SystemUserServiceFacade systemUserServiceFacade, PasswordEncoder passwordEncoder) {
		this.systemUserServiceFacade = systemUserServiceFacade;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	public ResponseEntity<Object> getSystemUsers() {
		return ResponseEntity.ok(systemUserServiceFacade.getAllManagerUsers());
	}

	@GetMapping("/users/me")
	public ResponseEntity<Object> getOwnSystemUser() {
		return ResponseEntity.ok(systemUserServiceFacade.getOwnManagerUser());
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<Object> getSystemUser(@PathVariable("username") String username) {
		return ResponseEntity.ok(systemUserServiceFacade.getOwnManagerUser());
	}

	@PostMapping("/users")
	public ResponseEntity<Object> postSystemUser(@Valid @RequestBody SystemUserRequestDto systemUserRequestDto) {
		systemUserRequestDto.setPassword(passwordEncoder.encode(systemUserRequestDto.getPassword()));
		return ResponseEntity.ok(systemUserServiceFacade.createManagerUser(systemUserRequestDto));
	}

	@PatchMapping("/users/{username}")
	public ResponseEntity<Object> patchSystemUser(@PathVariable("username") String username,
			@RequestBody SystemUserRequestDto systemUserRequestDto) {
		return ResponseEntity.ok(systemUserServiceFacade.updateSystemUser(username, systemUserRequestDto));
	}

	@DeleteMapping("/users/{username}")
	public ResponseEntity<Void> deleteSystemUser(@PathVariable("username") String username) {
		systemUserServiceFacade.deleteManagerUser(username);
		return ResponseEntity.noContent().build();
	}

}
