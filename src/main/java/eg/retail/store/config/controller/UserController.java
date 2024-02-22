package eg.retail.store.config.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eg.retail.store.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final AuthService authService;

	@GetMapping(value = "/login/{userId}")
	public ResponseEntity<String> login(
			@Valid @Pattern(regexp = "d+")@PathVariable("userId")int userId 
			) {
		 return ResponseEntity.ok().body(authService.createToken(userId));
	}
}
