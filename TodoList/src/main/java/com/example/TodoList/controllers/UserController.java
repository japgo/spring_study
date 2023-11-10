package com.example.TodoList.controllers;

import com.example.TodoList.dtos.ResponseDto;
import com.example.TodoList.dtos.SignupRequestDto;
import com.example.TodoList.dtos.UserResponseDto;
import com.example.TodoList.entities.User;
import com.example.TodoList.security.UserDetailsServiceImpl;
import com.example.TodoList.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public class UserController {

	private UserService userService;

	@Autowired
	public UserController( UserService userService ) {
		this.userService = userService;
	}

	@GetMapping( "/user" )
	public ResponseEntity< List< UserResponseDto > > getUsers() {
		List< UserResponseDto > usersList = userService.getUsers();

		return ResponseEntity.ok( usersList );
	}

	@PostMapping( "/user" )
	public ResponseEntity< ResponseDto > signupUser( @Valid SignupRequestDto signupRequestDto ) {
		userService.signupUser( signupRequestDto );

		return ResponseEntity.ok( new ResponseDto( "정상적으로 회원 가입 하였습니다.", 200 ) );
	}
}
