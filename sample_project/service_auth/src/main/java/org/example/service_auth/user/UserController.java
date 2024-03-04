package org.example.service_auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Value( "${server.port}" )
	private String port;

	@GetMapping("/")
	public ResponseEntity< String > hello() {
		return ResponseEntity.ok("Hello " + port);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup( @RequestBody SignupRequestDto signupRequestDto ) throws Exception {
		userService.signup( signupRequestDto );
		return ResponseEntity.ok("signup ok" );
	}
}
