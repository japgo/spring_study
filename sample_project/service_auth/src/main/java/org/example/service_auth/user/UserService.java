package org.example.service_auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public void signup(SignupRequestDto signupRequestDto ) throws Exception {
		if( !Objects.equals( signupRequestDto.password, signupRequestDto.passwordCheck ) ) {
			throw new Exception();
		}

		User user = User.builder()
				.userName( signupRequestDto.userName )
				.email( signupRequestDto.email )
				.password( signupRequestDto.password ).build();

		userRepository.save( user );
	}
}
