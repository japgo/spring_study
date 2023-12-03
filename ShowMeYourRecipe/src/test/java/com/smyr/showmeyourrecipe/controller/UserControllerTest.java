package com.smyr.showmeyourrecipe.controller;

import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.user.UserRoleEnum;
import com.smyr.showmeyourrecipe.security.UserDetailsImpl;
import com.smyr.showmeyourrecipe.service.EmailService;
import com.smyr.showmeyourrecipe.service.KakaoService;
import com.smyr.showmeyourrecipe.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( UserController.class )
@ActiveProfiles( "test" )
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private EmailService emailService;

	@MockBean
	private KakaoService kakaoService;

	@Test
	void getUser() throws Exception {
		// given
		User testUser = new User();
		testUser.setId( 1L );
		testUser.setUsername( "test" );
		testUser.setRole( UserRoleEnum.USER );
		testUser.setEmail( "test@test.com" );
		testUser.setPassword( "1234" );

		UserDetailsImpl userDetails = new UserDetailsImpl( testUser );

		SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(), userDetails.getAuthorities()
		) );

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/api/users/1" ).contentType( MediaType.APPLICATION_JSON );

		// when & then
		mockMvc.perform( requestBuilder )
				.andExpect( status().isOk() );
				//.andExpect( content().json( "" ) );
	}
}