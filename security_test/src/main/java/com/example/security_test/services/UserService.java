package com.example.security_test.services;

import com.example.security_test.dtos.SignupRequestDto;
import com.example.security_test.entities.User;
import com.example.security_test.repositories.UserRepository;
import com.mongodb.DuplicateKeyException;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder ) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void signup( SignupRequestDto signupRequestDto ) {
		String name = signupRequestDto.getName();
		String password = passwordEncoder.encode( signupRequestDto.getPassword() );

		User searchedUser = userRepository.findByName( name );
		if( searchedUser != null )
			throw new DuplicateRequestException( "name duplicated" );

		User user = new User( name, password );

		userRepository.save( user );
	}

}
