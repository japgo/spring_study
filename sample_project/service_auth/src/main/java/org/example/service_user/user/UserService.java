package org.example.service_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.AlreadyBoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public void signup(SignupRequestDto signupRequestDto ) throws ResponseStatusException {
		if( !Objects.equals( signupRequestDto.password, signupRequestDto.passwordCheck ) ) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Password not correct.");
		}

		if( userRepository.findByUserName( signupRequestDto.userName ).isPresent() ) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "This username already exists." );
		}

		User user = User.builder()
				.userName( signupRequestDto.userName )
				.email( signupRequestDto.email )
				.password( signupRequestDto.password ).build();

		userRepository.save( user );
	}
}
