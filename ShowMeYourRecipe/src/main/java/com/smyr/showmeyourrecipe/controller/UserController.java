package com.smyr.showmeyourrecipe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smyr.showmeyourrecipe.dto.user.UserRequestDto;

import com.smyr.showmeyourrecipe.dto.user.UserResponseDto;
import com.smyr.showmeyourrecipe.etc.response.ApiResponse;

import com.smyr.showmeyourrecipe.jwt.JwtUtil;
import com.smyr.showmeyourrecipe.security.UserDetailsImpl;
import com.smyr.showmeyourrecipe.service.EmailService;
import com.smyr.showmeyourrecipe.service.KakaoService;
import com.smyr.showmeyourrecipe.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api" )
public class UserController {
	private final UserService userService;
	private final EmailService emailService;
	private final KakaoService kakaoService;

	@PostMapping( "/auth/signup" )
	public @ResponseBody ResponseEntity< ApiResponse<?> > signup( @RequestBody UserRequestDto userRequestDto ) {
		this.emailService.sendEmailAuth( userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( userRequestDto.getEmail() + "으로 인증 메일을 발송하였습니다."  ) );
	}

	@GetMapping( "/auth/signup/email/{id}" )
	public ResponseEntity< ApiResponse<?> > email_auth( @PathVariable String id ) {
		this.userService.signupEmailAuth( id );

		return ResponseEntity.ok( ApiResponse.ok( "회원 가입을 축하합니다. 이제부터 로그인 가능합니다." ) );
	}

	@GetMapping( "/users/kakao/callback" )
	public ResponseEntity<?> kakaoLogin( @RequestParam String code, HttpServletResponse response ) throws JsonProcessingException, UnsupportedEncodingException {
		String token = kakaoService.kakaoLogin( code );

		token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
		Cookie cookie = new Cookie( JwtUtil.AUTHORIZATION_HEADER, token );
		cookie.setPath( "/" );

		response.addCookie( cookie );

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation( URI.create( "/" ) );
		return new ResponseEntity<>( headers, HttpStatus.MOVED_PERMANENTLY );
	}

	@GetMapping( "/users/logout" )
	public ResponseEntity<?> logout( HttpServletResponse response ) {
		Cookie cookie = new Cookie( JwtUtil.AUTHORIZATION_HEADER, "" );
		cookie.setPath( "/" );

		response.addCookie( cookie );

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation( URI.create( "/" ) );
		return new ResponseEntity<>( headers, HttpStatus.MOVED_PERMANENTLY );
	}

	@GetMapping( "/users/{userId}" )
	public ResponseEntity< ApiResponse<?> > getUser( @PathVariable long userId ) {
		var userResponseDto = this.userService.getUser( userId );

		return ResponseEntity.ok( ApiResponse.ok( userResponseDto ) );
	}

	@GetMapping( "/users/login-user" )
	public ResponseEntity< ApiResponse<?> > getLoginUser( @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) {
		var user = userDetailsImpl.getUser();

		return ResponseEntity.ok( ApiResponse.ok( new UserResponseDto( user ) ) );
	}


	@PatchMapping( "/users" )
	public ResponseEntity< ApiResponse<?> > updateProfile( @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, UserRequestDto userRequestDto ) {
		var userId = userDetailsImpl.getUser().getId();
		userService.updateUser( userId, userRequestDto );

		return ResponseEntity.ok( ApiResponse.ok( "update success" ) );
	}

}
