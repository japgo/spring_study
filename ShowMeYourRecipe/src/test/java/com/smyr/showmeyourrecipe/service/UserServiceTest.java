package com.smyr.showmeyourrecipe.service;

import com.smyr.showmeyourrecipe.dto.user.UserResponseDto;
import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.user.UserRoleEnum;
import com.smyr.showmeyourrecipe.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Test
	void getUser() {
		//given
		User user = new User( "username"
				, "password"
				, "test@test.com"
				, UserRoleEnum.USER
				, 1234L );
		user.setId( 1L );
		when( userRepository.save( any() ) ).thenReturn( user );

		BDDMockito.Then< UserResponseDto > then = then( userService.getUser( 1L ) );

		// then

	}

	@Test
	void updateUser() {

		//userService.updateUser(  );
	}

	@Test
	void signupEmailAuth() {
	}
}