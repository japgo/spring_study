package com.team10.backoffice.domain.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team10.backoffice.domain.users.dto.UserPasswordDto;
import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.domain.users.dto.UserResponseDto;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.service.KakaoServiceImpl;
import com.team10.backoffice.domain.users.service.UserServiceImpl;
import com.team10.backoffice.etc.response.ApiResponse;
import com.team10.backoffice.jwt.JwtUtil;
import com.team10.backoffice.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Tag(name = "users", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    //private final EmailService emailService;
    private final KakaoServiceImpl kakaoServiceImpl;

	@Operation(summary = "회원가입")
	@PostMapping("/auth/signup")
	public @ResponseBody ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
		this.userServiceImpl.signup( userRequestDto );
		return ResponseEntity.ok(ApiResponse.ok(userRequestDto.getUsername() + " 회원가입 성공!" ) );
	}
	// TODO 이부분은 안쓰이는 것 같아서 코드리뷰에 코멘트가 달리지 않을까하는 생각이 들기도 합니다
	@GetMapping("/auth/signup/email/{id}")
	public ResponseEntity<ApiResponse<?>> email_auth(@PathVariable String id) {
		//this.userService.signupEmailAuth( id );

		return ResponseEntity.ok(ApiResponse.ok("회원 가입을 축하합니다. 이제부터 로그인 가능합니다."));
	}
	@Operation(summary = "소셜 로그인 리다이렉션 url")
	@GetMapping("/users/kakao/callback")
	public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
		String token = kakaoServiceImpl.kakaoLogin( code );

		token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
		cookie.setPath("/");

		response.addCookie(cookie);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@Operation(summary = "로그아웃")
	@GetMapping("/users/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, "");
		cookie.setPath("/");

		response.addCookie(cookie);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/"));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@Operation(summary = "유저 정보 조회")
	@GetMapping("/users/{userId}")
	public ResponseEntity<ApiResponse<?>> getUser(@PathVariable String userId) {
		var userResponseDto = this.userServiceImpl.getUser(userId);

		return ResponseEntity.ok(ApiResponse.ok(userResponseDto));
	}

	@Operation(summary = "유저 삭제")
	@DeleteMapping( "/users/{userId}" )
	public ResponseEntity< ApiResponse< ? > > deleteUser( @PathVariable long userId,
	                                                      @AuthenticationPrincipal UserDetailsImpl userDetails )
	{
		User user = userDetails.getUser();
		userServiceImpl.deleteUser( userId, user );

		return ResponseEntity.ok( ApiResponse.ok( "delete success" ) );
	}
	@Operation(summary = "로그인")
	@GetMapping("/users/login-user")
	public ResponseEntity<ApiResponse<?>> getLoginUser(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		var user = userDetailsImpl.getUser();

		return ResponseEntity.ok(ApiResponse.ok(new UserResponseDto(user)));
	}


	@Operation(summary = "유저 정보 수정")
	@PatchMapping("/users")
	public ResponseEntity<ApiResponse<?>> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@Valid @RequestBody UserRequestDto userRequestDto) {
		var userId = userDetailsImpl.getUser().getId();
		userServiceImpl.updateUser(userId, userRequestDto);

		return ResponseEntity.ok(ApiResponse.ok("UPDATE SUCCESS"));
	}


	@Operation(summary = "패스워드 변경")
	@PatchMapping("/password")
		public ResponseEntity<ApiResponse<?>> updatePassword(
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @Valid @RequestBody UserPasswordDto userPasswordDto) {
		var userId = userDetailsImpl.getUser().getId();
		userServiceImpl.updatePassword(userId, userPasswordDto);

		return ResponseEntity.ok(ApiResponse.ok("UPDATE SUCCESS"));
	}
}