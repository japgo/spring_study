package org.example.service_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
@RestController
@RequestMapping("/api/users")
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

	@PostMapping("/login")
	public ResponseEntity<?> login( @RequestBody LoginRequestDto loginRequestDto ) {
		return ResponseEntity.ok( "login ok" );
	}
}
