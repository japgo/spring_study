package com.smyr.showmeyourrecipe.controller;

import com.smyr.showmeyourrecipe.dto.user.UserResponseDto;
import com.smyr.showmeyourrecipe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest( UserController.class )
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@DisplayName( "[API][GET] 특정 사용자 정보 조회" )
	@Test
	void getUser() {
		//given
//		given(userService.getUser( 10 ) )
//				.willReturn( UserResponseDto.builder()
//						.)
	}
}