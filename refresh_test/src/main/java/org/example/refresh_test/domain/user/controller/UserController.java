package org.example.refresh_test.domain.user.controller;

import io.micrometer.observation.transport.ResponseContext;
import lombok.RequiredArgsConstructor;
import org.example.refresh_test.domain.user.dto.UserRequestDto;
import org.example.refresh_test.domain.user.dto.UserResponseDto;
import org.example.refresh_test.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/users" )
public class UserController {

	private final UserService userService;
	@GetMapping( "" )
	public ResponseEntity< List< UserResponseDto > > get_users() {
		return ResponseEntity.ok( userService.get_users() );
	}

	@PostMapping( "/auth/signup" )
	public ResponseEntity< String > signup( UserRequestDto userRequestDto ) {
		userService.save_user( userRequestDto );
		return ResponseEntity.ok( "signup ok" );
	}

	@PostMapping( "/auth/logout" )
	public ResponseEntity< String > logout( @AuthenticationPrincipal UserDetails userDetails ) {
		 return ResponseEntity.ok( "logout ok" );
	}
}
