package org.example.service_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public void signup(SignupRequestDto signupRequestDto ) throws Exception {
		if( !Objects.equals( signupRequestDto.password, signupRequestDto.passwordCheck ) ) {
			throw new Exception();
		}

		if( userRepository.findByUserName( signupRequestDto.userName ).isPresent() ){
			throw new AlreadyBoundException();
		}

		User user = User.builder()
				.userName( signupRequestDto.userName )
				.email( signupRequestDto.email )
				.password( signupRequestDto.password ).build();

		userRepository.save( user );
	}
}
