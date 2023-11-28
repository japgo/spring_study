package com.smyr.showmeyourrecipe.controller;

import com.smyr.showmeyourrecipe.dto.user.UserRequestDto;

import com.smyr.showmeyourrecipe.etc.response.ApiResponse;

import com.smyr.showmeyourrecipe.security.UserDetailsImpl;
import com.smyr.showmeyourrecipe.service.EmailService;
import com.smyr.showmeyourrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api" )
public class UserController {
	private final UserService userService;
	private final EmailService emailService;

	@PostMapping( "/auth/signup" )
	public @ResponseBody ResponseEntity< ApiResponse > signup( @RequestBody UserRequestDto userRequestDto ) {
		this.emailService.sendEmailAuth( userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( userRequestDto.getEmail() + "으로 인증 메일을 발송하였습니다."  ) );
	}

	@GetMapping( "/auth/signup/email/{id}" )
	public ResponseEntity< ApiResponse > email_auth( @PathVariable String id ) {
		this.userService.signupEmailAuth( id );

		return ResponseEntity.ok( ApiResponse.ok( "회원 가입을 축하합니다. 이제부터 로그인 가능합니다." ) );
	}

	@GetMapping( "/users/{userId}" )
	public ResponseEntity< ApiResponse > getUser( @PathVariable long userId ) {
		var userResponseDto = this.userService.getUser( userId );

		return ResponseEntity.ok( ApiResponse.ok( userResponseDto ) );
	}

	@PatchMapping( "/users" )
	public ResponseEntity< ApiResponse > updateProfile( @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, UserRequestDto userRequestDto ) {
		var userId = userDetailsImpl.getUser().getId();
		userService.updateUser( userId, userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( "update success" ) );
	}

}
