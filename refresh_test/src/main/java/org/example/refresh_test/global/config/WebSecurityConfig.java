package org.example.refresh_test.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity ) throws Exception {
		httpSecurity.csrf( AbstractHttpConfigurer::disable );

		httpSecurity.sessionManagement( sm -> sm.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );

		httpSecurity.authorizeHttpRequests( auth -> auth
				.requestMatchers( "/api/auth/**" ).permitAll()
				.anyRequest().authenticated()
		);

		return httpSecurity.build();
	}
}
