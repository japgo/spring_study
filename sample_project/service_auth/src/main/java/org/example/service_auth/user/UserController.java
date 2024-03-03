package org.example.service_auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Value( "${server.port}" )
	private String port;

	@GetMapping("/")
	public ResponseEntity< String > hello() {
		return ResponseEntity.ok("Hello " + port);
	}

	@GetMapping("/signup")
	public ResponseEntity<?> signup( SignupRequestDto signupRequestDto ) throws Exception {
		userService.signup( signupRequestDto );
		return ResponseEntity.ok("signup ok" );
	}
}
