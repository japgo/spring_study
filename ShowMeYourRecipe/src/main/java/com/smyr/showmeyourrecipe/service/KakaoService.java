package com.smyr.showmeyourrecipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smyr.showmeyourrecipe.dto.user.KakaoUserInfoDto;
import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.user.UserRoleEnum;
import com.smyr.showmeyourrecipe.jwt.JwtUtil;
import com.smyr.showmeyourrecipe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;

	public String kakaoLogin( String code ) throws JsonProcessingException {

		// 인가 코드로 액세스 토큰 요청
		String accessToken = getToken( code );

		// 토큰으로 카카로 API 호출 : 엑세스 토큰으로 카카오 사용자 정보 가져오기.
		KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo( accessToken );

		User kakaoUser = registerKakaoUserIfNeeded( kakaoUserInfo );

		String createToken = jwtUtil.createToken( kakaoUser.getId(), kakaoUser.getRole() );

		return createToken;
	}

	private User registerKakaoUserIfNeeded( KakaoUserInfoDto kakaoUserInfoDto ) {
		Long kakaoId = kakaoUserInfoDto.getId();
		User kakaoUser = userRepository.findByKakaoId( kakaoId ).orElse( null );

		if( kakaoUser == null ) {
			String kakaoEmail = kakaoUserInfoDto.getEmail();
			User sameEmailUser = userRepository.findByEmail( kakaoEmail ).orElse( null );
			if( sameEmailUser != null ) {
				kakaoUser = sameEmailUser;
				kakaoUser = kakaoUser.kakaoIdUpdate( kakaoId );
			}
			else {
				String password = UUID.randomUUID().toString();
				String encodePassword = passwordEncoder.encode( password );

				String email = kakaoUserInfoDto.getEmail();

				kakaoUser = new User( kakaoUserInfoDto.getNickname(), encodePassword, email, UserRoleEnum.USER, kakaoId );
			}

			userRepository.save( kakaoUser );
		}

		return kakaoUser;
	}

	private String getToken( String code ) throws JsonProcessingException {
		URI uri = UriComponentsBuilder
				.fromUriString( "https://kauth.kakao.com" )
				.path( "/oauth/token" )
				.encode()
				.build()
				.toUri();

		// Http Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add( "Content-type", "application/x-www-form-urlencoded;charset=utf-8" );

		// Http Body 생성
		MultiValueMap< String, String > body = new LinkedMultiValueMap<>();
		body.add( "grant_type", "authorization_code" );
		body.add( "client_id", "a875e56e1fed879c186872e87ce0c380" );
		body.add( "redirect_uri", "http://localhost:8080/api/users/kakao/callback" );
		body.add( "code", code );

		RequestEntity< MultiValueMap< String, String > > requestEntity = RequestEntity
				.post( uri )
				.headers( headers )
				.body( body );

		// Http 요청 보내기
		ResponseEntity< String > response = restTemplate.exchange(
				requestEntity,
				String.class
		);

		// Http 응답 ( JSON ) -> 액세스 토큰 파싱
		JsonNode jsonNode = new ObjectMapper().readTree( response.getBody() );

		return jsonNode.get( "access_token" ).asText();
	}

	private KakaoUserInfoDto getKakaoUserInfo( String accessToken ) throws JsonProcessingException {
		URI uri = UriComponentsBuilder
				.fromUriString( "https://kapi.kakao.com" )
				.path( "/v2/user/me" )
				.encode()
				.build()
				.toUri();

		// Http header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add( "Authorization", "Bearer " + accessToken );
		headers.add( "Content-type", "application/x-www-form-urlencoded;charset=utf-8" );

		RequestEntity< MultiValueMap< String, String > > requestEntity = RequestEntity
				.post( uri )
				.headers( headers )
				.body( new LinkedMultiValueMap<>() );

		// Http 요청 보내기
		ResponseEntity< String > response = restTemplate.exchange(
				requestEntity,
				String.class
		);

		JsonNode jsonNode = new ObjectMapper().readTree( response.getBody() );
		Long id = jsonNode.get( "id" ).asLong();
		String nickname = jsonNode.get( "properties" )
				.get( "nickname" ).asText();
		String email = jsonNode.get( "kakao_account" )
				.get( "email" ).asText();

		log.info( "카카오 사용자 정보: " + id + ", " + nickname + ", " + email );

		return new KakaoUserInfoDto( id, nickname, email );
	}
}
