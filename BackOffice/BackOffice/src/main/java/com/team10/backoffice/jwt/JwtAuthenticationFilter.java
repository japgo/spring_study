package com.team10.backoffice.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import com.team10.backoffice.domain.users.repository.UserRepository;
import com.team10.backoffice.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        // 필터를 적용할 경로 설정
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            var inputStream = request.getInputStream();
            UserRequestDto requestDto = new ObjectMapper().readValue(inputStream, UserRequestDto.class);
            log.info("------" + requestDto.toString());

            if (isUserBlocked(requestDto.getUsername())) {
                throw new BadCredentialsException("차단된 사용자입니다");
            }
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
      log.info("로그인 성공 및 JWT 생성");
      String username = (( UserDetailsImpl ) authResult.getPrincipal()).getUser().getUsername();
      UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

      String token = jwtUtil.createToken(username, role);
      jwtUtil.addJwtToCookie(token, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }

    private boolean isUserBlocked(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        boolean isBlocked = userOptional.map(User::isBlocked).orElse(false);
        log.info("User ID : '{}' 은/는 차단된 사용자입니다", username);

        return isBlocked;
    }
}
