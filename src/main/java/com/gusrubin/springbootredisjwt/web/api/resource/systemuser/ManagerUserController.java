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
public class ManagerUserController {

	private final ManagerUserServiceFacade managerUserServiceFacade;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ManagerUserController(ManagerUserServiceFacade managerUserServiceFacade, PasswordEncoder passwordEncoder) {
		this.managerUserServiceFacade = managerUserServiceFacade;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	public ResponseEntity<Object> getManagerUsers() {
		return ResponseEntity.ok(managerUserServiceFacade.getAllManagerUsers());
	}

	@GetMapping("/users/me")
	public ResponseEntity<Object> getOwnManagerUser() {
		return ResponseEntity.ok(managerUserServiceFacade.getOwnManagerUser());
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<Object> getManagerUser(@PathVariable("username") String username) {
		return ResponseEntity.ok(managerUserServiceFacade.getOwnManagerUser());
	}

	@PostMapping("/users")
	public ResponseEntity<Object> postManagerUser(@Valid @RequestBody ManagerUserRequestDto managerUserRequestDto) {
		managerUserRequestDto.setPassword(passwordEncoder.encode(managerUserRequestDto.getPassword()));
		return ResponseEntity.ok(managerUserServiceFacade.createManagerUser(managerUserRequestDto));
	}

	@PatchMapping("/users/{username}")
	public ResponseEntity<Object> patchManagerUser(@PathVariable("username") String username,
			@RequestBody ManagerUserRequestDto managerUserRequestDto) {
		return ResponseEntity.ok(managerUserServiceFacade.updateManagerUser(username, managerUserRequestDto));
	}

	@DeleteMapping("/users/{username}")
	public ResponseEntity<Void> deleteManagerUser(@PathVariable("username") String username) {
		managerUserServiceFacade.deleteManagerUser(username);
		return ResponseEntity.noContent().build();
	}

}
