package com.example.TodoList.services;

import com.example.TodoList.dtos.SignupRequestDto;
import com.example.TodoList.dtos.UserResponseDto;
import com.example.TodoList.entities.User;
import com.example.TodoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder ) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List< UserResponseDto > getUsers() {
		return userRepository.findAll().stream().map( UserResponseDto::new ).toList();
	}

	public void signupUser( SignupRequestDto signupRequestDto ) {
		String username = signupRequestDto.getUsername();
		String password = passwordEncoder.encode( signupRequestDto.getPassword() );

		User user = new User( username, password );
		userRepository.save( user );
	}
}
