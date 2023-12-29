package org.example.refresh_test.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.refresh_test.domain.user.dto.UserRequestDto;
import org.example.refresh_test.domain.user.dto.UserResponseDto;
import org.example.refresh_test.domain.user.entity.User;
import org.example.refresh_test.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public List< UserResponseDto > get_users() {
		return userRepository.findAll().stream().map( i -> UserResponseDto.builder()
				.username( i.getUsername() )
				.build()
		).toList();
	}

	public void save_user( UserRequestDto userRequestDto ) {
		String pw = "";
		userRepository.save( User.builder()
				.username( userRequestDto.getUsername() )
				.password( pw )
				.build()
		);
	}
}
