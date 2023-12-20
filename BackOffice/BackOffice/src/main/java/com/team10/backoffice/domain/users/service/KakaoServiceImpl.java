package com.team10.backoffice.domain.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team10.backoffice.domain.users.dto.KakaoUserInfoDto;
import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.domain.users.dto.UserResponseDto;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import com.team10.backoffice.domain.users.repository.UserRepository;
import com.team10.backoffice.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoServiceImpl implements UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public String kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        log.info("토큰 : " + accessToken);
        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        UserResponseDto kakaoUserInfo = getUser(accessToken);
        // 3.필요시 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        // 4.jwt토큰 발급
        String createdToken = jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole());
        return createdToken;

    }
    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "48ecdcfda06ec7bafebfb0d8f59fd9b0");
        body.add("redirect_uri", "http://localhost:8080/api/users/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    @Override
    public void signup( UserRequestDto userRequestDto ) {

    }

    public UserResponseDto getUser( String identifier ) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + identifier );
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

	    JsonNode jsonNode = null;
	    try {
		    jsonNode = new ObjectMapper().readTree(response.getBody());
	    }
	    catch( JsonProcessingException e ) {
		    throw new RuntimeException( e );
	    }
	    Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername( nickname );
        userResponseDto.setNickname( nickname );
        userResponseDto.setEmail( email );
        return userResponseDto;
    }


    private User registerKakaoUserIfNeeded( UserResponseDto kakaoUserInfo ) {

        // DB 에 중복된 Kakao Id 가 있는지 확인
        String kakaoId = kakaoUserInfo.getUsername();
        User kakaoUser = userRepository.findByUsername(kakaoId).orElse(null);

        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                //kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                String nickname = kakaoUserInfo.getEmail();

                kakaoUser = new User(kakaoUserInfo.getEmail(), nickname, encodedPassword, email, UserRoleEnum.USER, 0L );
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

}
