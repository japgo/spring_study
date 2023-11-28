package com.example.security_test.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {

		// 설정 안하면 post 동작 시 403 반환 됨.
		http.csrf( AbstractHttpConfigurer::disable );

		http
				.authorizeHttpRequests( ( authorizeHttpRequests ) -> authorizeHttpRequests
						.requestMatchers( "/api/auth/**" ).permitAll() );

		return http.build();
	}
}
