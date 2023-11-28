package com.example.security_test.controllers;

import com.example.security_test.dtos.SignupRequestDto;
import com.example.security_test.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api" )
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController( UserService userService ) {
		this.userService = userService;
	}

	@PostMapping( "/auth/signup" )
	public ResponseEntity signup( @Valid SignupRequestDto signupRequestDto ) {
		userService.signup( signupRequestDto );

		return ResponseEntity.ok().body( "signup ok" );
	}

	@GetMapping( "/auth/login" )
	public String login() {
		return "test1";
	}
}
